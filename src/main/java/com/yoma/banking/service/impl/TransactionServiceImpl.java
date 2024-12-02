package com.yoma.banking.service.impl;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.model.Transaction;
import com.yoma.banking.repository.TransactionRepository;
import com.yoma.banking.repository.AccountRepository;
import com.yoma.banking.service.AccountBalanceValidator;
import com.yoma.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountBalanceValidator accountBalanceValidator;
    private final AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountBalanceValidator accountBalanceValidator,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountBalanceValidator = accountBalanceValidator;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void createTransaction(TransactionDto transactionDto) {
        // Step 1: Check if sufficient balance is available
        if (!accountBalanceValidator.hasSufficientBalance(transactionDto.getFromAccountNo(), transactionDto.getAmount())) {
            throw new IllegalArgumentException("Insufficient balance in the account.");
        }

        // Step 2: Debit from fromAccountNo
        var fromAccount = accountRepository.findByAccountNo(transactionDto.getFromAccountNo())
                .orElseThrow(() -> new IllegalArgumentException("From account not found"));
        System.out.println("From account initial balance: " + fromAccount.getBalance());

        // Subtract the amount from the account balance
        BigDecimal updatedFromBalance = fromAccount.getBalance().subtract(transactionDto.getAmount());
        fromAccount.setBalance(updatedFromBalance);
        fromAccount.setUpdatedDate(LocalDateTime.now());

        System.out.println("From account updated balance after subtraction: " + fromAccount.getBalance());
        accountRepository.saveAndFlush(fromAccount);
        entityManager.flush();

        // Step 3: Credit to toAccountNo
        var toAccount = accountRepository.findByAccountNo(transactionDto.getToAccountNo())
                .orElseThrow(() -> new IllegalArgumentException("To account not found"));
        System.out.println("To account initial balance: " + toAccount.getBalance());

        // Add the amount to the toAccount balance
        BigDecimal updatedToBalance = toAccount.getBalance().add(transactionDto.getAmount());
        toAccount.setBalance(updatedToBalance);
        toAccount.setUpdatedDate(LocalDateTime.now());

        System.out.println("To account updated balance after credit: " + toAccount.getBalance());
        accountRepository.saveAndFlush(toAccount);
        entityManager.flush();

        // Step 4: Create the transaction
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionNo(transactionDto.getTransactionNo())
                .fromAccountNo(transactionDto.getFromAccountNo())
                .toAccountNo(transactionDto.getToAccountNo())
                .amount(transactionDto.getAmount())
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
    }


    @Override
    public List<Transaction> getTransactionHistory(LocalDateTime fromDate, LocalDateTime toDate) {
        return transactionRepository.findByTransactionDateBetween(fromDate, toDate);
    }
}
