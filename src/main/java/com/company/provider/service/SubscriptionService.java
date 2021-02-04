package com.company.provider.service;

import com.company.provider.dto.SubscriptionDto;
import com.company.provider.entity.*;
import com.company.provider.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    private UserService userService;
    private AccountService accountService;
    private TariffService tariffService;
    private StatusService statusService;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            UserService userService,
            AccountService accountService,
            TariffService tariffService,
            StatusService statusService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.accountService = accountService;
        this.tariffService = tariffService;
        this.statusService = statusService;
    }

    public void subscribe(SubscriptionDto subscriptionDto) {
        User user = userService.createUser(subscriptionDto);
        accountService.createAccount(user);

        Tariff tariff = tariffService.loadTariffById(subscriptionDto.getTariffId());

        Set<Tariff> tariffs = new HashSet<>();
        tariffs.add(tariff);

        Status status = statusService.getStatusByName("NOT_ACTIVE");

        createSubscription(user, tariffs, status);
    }

    public Subscription createSubscription(User user, Set<Tariff> tariffs, Status status) {
        Double price = tariffs.stream().map(Tariff::getPrice).reduce(0.0, Double::sum);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStatus(status);
        subscription.setTariffs(tariffs);
        subscription.setPrice(price);

        return subscriptionRepository.save(subscription);
    }
}
