package com.yoma.banking.service;

import java.math.BigDecimal;

public interface AccountBalanceValidator {
    boolean hasSufficientBalance(String accountNo, BigDecimal amount);
}

