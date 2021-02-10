package com.company.provider.service;

import com.company.provider.entity.Account;
import com.company.provider.entity.User;
import com.company.provider.exeption.InsufficientFundsException;
import com.company.provider.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(User user) {
        Account account = new Account();
        account.setAmount(0.0);
        account.setUser(user);

        accountRepository.save(account);
    }

    public void depositAccount(Account account, Double amount) {
        Double currentAmount = account.getAmount();
        account.setAmount(currentAmount + amount);
        accountRepository.save(account);
    }

    public boolean debitAccount(Account account, Double amount) throws InsufficientFundsException {
        Double currentAmount = account.getAmount();

        if (currentAmount >= amount) {
            Double newAmount = currentAmount - amount;
            account.setAmount(newAmount);
            accountRepository.save(account);

            return true;
        }
        throw new InsufficientFundsException("insufficient funds in the account");
    }
}
