package com.yoma.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @Column(name = "TransactionID", nullable = false, updatable = false)
    private String transactionId;

    @Column(name = "TransactionNo", nullable = false, length = 30)
    private String transactionNo;

    @Column(name = "FromAccountNo", nullable = false, length = 30)
    private String fromAccountNo;

    @Column(name = "ToAccountNo", nullable = false, length = 30)
    private String toAccountNo;

    @Column(name = "Amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "TransactionDate", nullable = false)
    private LocalDateTime transactionDate;
}
