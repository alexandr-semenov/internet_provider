package com.company.provider.service;

import com.company.provider.entity.Account;
import com.company.provider.entity.User;
import com.company.provider.events.AccountEventPublisher;
import com.company.provider.exeption.RestException;
import com.company.provider.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final AccountEventPublisher accountEventPublisher;

    public AccountService(AccountRepository accountRepository, AccountEventPublisher accountEventPublisher) {
        this.accountRepository = accountRepository;
        this.accountEventPublisher = accountEventPublisher;
    }

    public void createAccount(User user) {
        Account account = Account.builder()
                .setAmount(0.0)
                .setUser(user)
                .build();

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
            logger.error(e.getMessage());
            throw new RestException("deposit_fail");
        }
    }

    public void debitAccount(User user) {
        Double amount = user.getSubscription().getPrice();
        Account account = user.getAccount();
        Double currentAmount = account.getAmount();

        if (currentAmount >= amount) {
            Double newAmount = currentAmount - amount;
            account.setAmount(newAmount);
            accountRepository.save(account);

            accountEventPublisher.publishDebitSuccessEvent(user);
        }
    }
}
