package com.yoma.banking.service;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void createTransaction(TransactionDto transactionDto);
    List<Transaction> getTransactionHistory(LocalDateTime fromDate, LocalDateTime toDate);
}
