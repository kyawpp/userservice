package com.yoma.banking.controller;

import com.yoma.banking.dto.TransactionDto;
import com.yoma.banking.dto.TransactionHistoryRequest;
import com.yoma.banking.model.Transaction;
import com.yoma.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid TransactionDto transactionDto) {
        transactionService.createTransaction(transactionDto);
        return ResponseEntity.ok("Transaction created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TransactionHistoryRequest request) {
        LocalDateTime fromDate = request.getFromDate().atStartOfDay();
        LocalDateTime toDate = request.getToDate().atTime(LocalTime.MAX);

        List<Transaction> transactions = transactionService.getTransactionHistory(fromDate, toDate);
        return ResponseEntity.ok(transactions);
    }
}
