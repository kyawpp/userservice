package com.yoma.banking.service;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.model.Transaction;
import com.yoma.banking.repository.TransactionRepository;
import com.yoma.banking.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void createTransaction_Success() {
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionNo("TX001")
                .fromAccountNo("ACC001")
                .toAccountNo("ACC002")
                .amount(BigDecimal.valueOf(5000))
                .build();

        transactionService.createTransaction(transactionDto);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransactionHistory_Success() {
        LocalDateTime now = LocalDateTime.now();
        Transaction mockTransaction = Transaction.builder()
                .transactionId("123")
                .transactionNo("TX001")
                .fromAccountNo("ACC001")
                .toAccountNo("ACC002")
                .amount(BigDecimal.valueOf(5000))
                .transactionDate(now)
                .build();

        when(transactionRepository.findByTransactionDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(mockTransaction));

        List<Transaction> transactions = transactionService.getTransactionHistory(now.minusDays(1), now);

        assertEquals(1, transactions.size());
        assertEquals("TX001", transactions.get(0).getTransactionNo());
    }
}
