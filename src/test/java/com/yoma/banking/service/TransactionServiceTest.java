package com.yoma.banking.service;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.model.Account;
import com.yoma.banking.model.Transaction;
import com.yoma.banking.repository.AccountRepository;
import com.yoma.banking.repository.TransactionRepository;
import com.yoma.banking.service.impl.TransactionServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountBalanceValidator accountBalanceValidator;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(transactionRepository, accountBalanceValidator, accountRepository, entityManager);
    }

    @Test
    void createTransaction_Success() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionNo("TXN12345");
        transactionDto.setFromAccountNo("1234567890");
        transactionDto.setToAccountNo("0987654321");
        transactionDto.setAmount(new BigDecimal("100.00"));

        Account fromAccount = new Account();
        fromAccount.setAccountNo("1234567890");
        fromAccount.setBalance(new BigDecimal("500.00"));

        Account toAccount = new Account();
        toAccount.setAccountNo("0987654321");
        toAccount.setBalance(new BigDecimal("300.00"));

        // Mock behavior
        when(accountRepository.findByAccountNo("1234567890")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNo("0987654321")).thenReturn(Optional.of(toAccount));

        // Force balance validator to return true
        when(accountBalanceValidator.hasSufficientBalance(eq("1234567890"), eq(new BigDecimal("100.00")))).thenReturn(true);

        // Mock EntityManager flush() method
        doNothing().when(entityManager).flush();

        // Act
        assertDoesNotThrow(() -> transactionService.createTransaction(transactionDto), "Exception should not have been thrown");

        // Assert
        verify(accountRepository, times(1)).saveAndFlush(fromAccount);
        verify(accountRepository, times(1)).saveAndFlush(toAccount);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransactionHistory_Success() {
        // Arrange
        LocalDateTime fromDate = LocalDateTime.now().minusDays(1);
        LocalDateTime toDate = LocalDateTime.now();

        // Mock behavior
        when(transactionRepository.findByTransactionDateBetween(fromDate, toDate)).thenReturn(List.of(new Transaction()));

        // Act
        List<Transaction> transactions = transactionService.getTransactionHistory(fromDate, toDate);

        // Assert
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }
}
