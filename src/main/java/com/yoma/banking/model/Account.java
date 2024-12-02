package com.yoma.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @Column(name = "AccountID", nullable = false, updatable = false)
    private String accountId;

    @Column(name = "AccountNo", nullable = false, length = 30)
    private String accountNo;

    @Column(name = "AccountHolderName", nullable = false, length = 30)
    private String accountHolderName;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "CreatedDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "UpdatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "Balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;
}
