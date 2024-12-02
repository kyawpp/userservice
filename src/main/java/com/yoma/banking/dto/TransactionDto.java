package com.yoma.banking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    @NotBlank(message = "Transaction number is required")
    private String transactionNo;

    @NotBlank(message = "From account number is required")
    private String fromAccountNo;

    @NotBlank(message = "To account number is required")
    private String toAccountNo;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
