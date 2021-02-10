package com.company.provider.service;

import com.company.provider.entity.Account;
import com.company.provider.entity.User;
import com.company.provider.events.AccountEventPublisher;
import com.company.provider.exeption.InsufficientFundsException;
import com.company.provider.exeption.RestException;
import com.company.provider.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private AccountEventPublisher accountEventPublisher;

    public AccountService(
            AccountRepository accountRepository,
            AccountEventPublisher accountEventPublisher
    ) {
        this.accountRepository = accountRepository;
        this.accountEventPublisher = accountEventPublisher;
    }

    public void createAccount(User user) {
        Account account = new Account();
        account.setAmount(0.0);
        account.setUser(user);

        accountRepository.save(account);
    }

    public void depositAccount(User user, Double amount) {
        try {
            Account account = user.getAccount();

            Double currentAmount = account.getAmount();
            account.setAmount(currentAmount + amount);
            accountRepository.save(account);

            accountEventPublisher.publishDepositSuccessEvent(user);
        } catch (Exception e) {
            throw new RestException("deposit_fail");
        }

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
