package com.company.provider.events;

import com.company.provider.service.SubscriptionService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {
    private final SubscriptionService subscriptionService;

    public AccountEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @EventListener
    public void handleContextRefresh(AccountDepositSuccessEvent event) {
        subscriptionService.tryToActivateSubscription(event.getUser());
    }
}
