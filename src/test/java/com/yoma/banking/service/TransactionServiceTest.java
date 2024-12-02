package com.yoma.banking.service;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.model.Account;
import com.yoma.banking.model.Transaction;
import com.yoma.banking.repository.AccountRepository;
import com.yoma.banking.repository.TransactionRepository;
import com.yoma.banking.service.AccountBalanceValidator;
import com.yoma.banking.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountBalanceValidator accountBalanceValidator;

    @InjectMocks
    private TransactionServiceImpl transactionService;

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

        // Act
        assertDoesNotThrow(() -> transactionService.createTransaction(transactionDto), "Exception should not have been thrown");

        // Assert
        verify(accountRepository, times(1)).saveAndFlush(fromAccount);
        verify(accountRepository, times(1)).saveAndFlush(toAccount);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}
