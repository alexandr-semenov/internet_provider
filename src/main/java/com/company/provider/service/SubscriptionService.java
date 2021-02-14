package com.company.provider.service;

import com.company.provider.dto.SubscriptionDto;
import com.company.provider.dto.TariffDto;
import com.company.provider.entity.Subscription;
import com.company.provider.entity.Tariff;
import com.company.provider.entity.User;
import com.company.provider.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final AccountService accountService;
    private final TariffService tariffService;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            UserService userService,
            AccountService accountService,
            TariffService tariffService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.accountService = accountService;
        this.tariffService = tariffService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void subscribe(SubscriptionDto subscriptionDto) {
        User user = userService.createUser(subscriptionDto);
        accountService.createAccount(user);

        List<Long> ids = subscriptionDto.getTariffs().stream().map(TariffDto::getId).collect(Collectors.toList());

        List<Tariff> tariffs = tariffService.loadMultipleTariff(ids);

        createSubscription(user, tariffs);
    }

    public void createSubscription(User user, List<Tariff> tariffs) {
        Double price = tariffs.stream().map(Tariff::getPrice).reduce(0.0, Double::sum);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStatus(Subscription.Status.NOT_ACTIVE);
        subscription.setTariffs(tariffs);
        subscription.setPrice(price);
        subscriptionRepository.save(subscription);
    }

    public void activateSubscription(User user) {
        Subscription subscription = user.getSubscription();
        subscription.setStatus(Subscription.Status.ACTIVE);
        subscriptionRepository.save(subscription);
    }
}
