package com.yoma.banking;

import com.yoma.banking.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BankingApplicationTests {

	@MockBean
	private UserService userService;

	@MockBean
	private AccountService accountService;

	@MockBean
	private TransactionService transactionService;

	@MockBean
	private AccountBalanceValidator accountBalanceValidator;

	@Test
	void contextLoads() {
		// This test simply checks if the Spring application context loads successfully
	}
}
