package com.yoma.banking.service.impl;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.model.Transaction;
import com.yoma.banking.repository.TransactionRepository;
import com.yoma.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void createTransaction(TransactionDto transactionDto) {
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
