package com.company.provider.events;

import com.company.provider.entity.User;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AccountEventPublisher {
    private final ApplicationEventPublisher publisher;

    public AccountEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishDepositSuccessEvent(User user) {
        publisher.publishEvent(new AccountDepositSuccessEvent(this, user));
    }

    public void publishDebitSuccessEvent(User user) {
        publisher.publishEvent(new AccountDebitSuccessEvent(this, user));
    }
}
