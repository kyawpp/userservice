package com.yoma.banking.service.impl;

import com.yoma.banking.repository.AccountRepository;
import com.yoma.banking.service.AccountBalanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountBalanceValidatorImpl implements AccountBalanceValidator {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountBalanceValidatorImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean hasSufficientBalance(String accountNo, BigDecimal amount) {
        BigDecimal currentBalance = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"))
                .getBalance();

        return currentBalance.compareTo(amount) >= 0;
    }

}
