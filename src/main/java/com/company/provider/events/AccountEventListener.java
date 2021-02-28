package com.company.provider.events;

import com.company.provider.service.AccountService;
import com.company.provider.service.SubscriptionService;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {
    private final SubscriptionService subscriptionService;
    private final AccountService accountService;

    public AccountEventListener(SubscriptionService subscriptionService, AccountService accountService) {
        this.subscriptionService = subscriptionService;
        this.accountService = accountService;
    }

    @EventListener
    public void handleContextRefresh(AccountDepositSuccessEvent event) {
        accountService.debitAccount(event.getUser());
    }

    @EventListener
    public void handleContextRefresh(AccountDebitSuccessEvent event) {
        subscriptionService.activateSubscription(event.getUser());
    }
}
