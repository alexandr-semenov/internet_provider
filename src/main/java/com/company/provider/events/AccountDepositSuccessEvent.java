package com.company.provider.events;

import com.company.provider.entity.User;

import org.springframework.context.ApplicationEvent;

public class AccountDepositSuccessEvent extends ApplicationEvent {
    private final User user;

    public AccountDepositSuccessEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
