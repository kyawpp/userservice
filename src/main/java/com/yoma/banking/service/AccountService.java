package com.yoma.banking.service;

import com.yoma.banking.dto.AccountDto;

public interface AccountService {
    void createAccount(AccountDto accountDto, String userId);
}
