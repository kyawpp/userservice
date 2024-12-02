package com.yoma.banking.service;

import com.yoma.banking.dto.AccountDto;
import com.yoma.banking.model.Account;
import com.yoma.banking.model.User;
import com.yoma.banking.repository.AccountRepository;
import com.yoma.banking.repository.UserRepository;
import com.yoma.banking.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_Success() {
        User mockUser = User.builder()
                .userId("123")
                .userName("John Doe")
                .email("john.doe@example.com")
                .password("hashedPassword")
                .build();

        AccountDto accountDto = AccountDto.builder()
                .accountNo("ACC001")
                .accountHolderName("John Doe")
                .balance(BigDecimal.valueOf(10000))
                .build();

        when(userRepository.findById("123")).thenReturn(Optional.of(mockUser));

        accountService.createAccount(accountDto, "123");

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccount_UserNotFound() {
        AccountDto accountDto = AccountDto.builder()
                .accountNo("ACC001")
                .accountHolderName("John Doe")
                .balance(BigDecimal.valueOf(10000))
                .build();

        when(userRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(accountDto, "123"));
    }
}
