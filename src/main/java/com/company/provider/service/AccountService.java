package com.company.provider.service;

import com.company.provider.entity.Account;
import com.company.provider.entity.User;
import com.company.provider.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(User user) {
        Account account = new Account();
        account.setAmount(0.0);
        account.setUser(user);

        return accountRepository.save(account);
    }
}
