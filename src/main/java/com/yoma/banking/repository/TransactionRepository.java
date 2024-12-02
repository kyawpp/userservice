package com.yoma.banking.repository;

import com.yoma.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByTransactionDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
