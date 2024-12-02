package com.yoma.banking.service.impl;

import com.yoma.banking.dto.AccountDto;
import com.yoma.banking.model.Account;
import com.yoma.banking.model.User;
import com.yoma.banking.repository.AccountRepository;
import com.yoma.banking.repository.UserRepository;
import com.yoma.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createAccount(AccountDto accountDto, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account account = Account.builder()
                .accountId(UUID.randomUUID().toString())
                .accountNo(accountDto.getAccountNo())
                .accountHolderName(accountDto.getAccountHolderName())
                .balance(accountDto.getBalance())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();

        accountRepository.save(account);
    }
}
