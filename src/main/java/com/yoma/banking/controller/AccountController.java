package com.yoma.banking.controller;

import com.yoma.banking.dto.AccountDto;
import com.yoma.banking.service.AccountService;
import com.yoma.banking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AccountController(AccountService accountService, JwtUtil jwtUtil) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody @Valid AccountDto accountDto) {
        String token = authorizationHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);
        System.out.println("userId: " + userId);

        accountService.createAccount(accountDto, userId);

        return ResponseEntity.status(201).body("Account created successfully");
    }
}
