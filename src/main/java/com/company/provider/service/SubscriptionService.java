package com.company.provider.service;

import com.company.provider.dto.SubscriptionDto;
import com.company.provider.dto.TariffDto;
import com.company.provider.entity.Status;
import com.company.provider.entity.Subscription;
import com.company.provider.entity.Tariff;
import com.company.provider.entity.User;
import com.company.provider.exeption.InsufficientFundsException;
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
    private final StatusService statusService;

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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void subscribe(SubscriptionDto subscriptionDto) {
        User user = userService.createUser(subscriptionDto);
        accountService.createAccount(user);

        List<Long> ids = subscriptionDto.getTariffs().stream().map(TariffDto::getId).collect(Collectors.toList());

        List<Tariff> tariffs = tariffService.loadMultipleTariff(ids);

        Status status = statusService.getStatusByName("NOT_ACTIVE");

        createSubscription(user, tariffs, status);
    }

    public void createSubscription(User user, List<Tariff> tariffs, Status status) {
        Double price = tariffs.stream().map(Tariff::getPrice).reduce(0.0, Double::sum);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStatus(status);
        subscription.setTariffs(tariffs);
        subscription.setPrice(price);

        subscriptionRepository.save(subscription);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void tryToActivateSubscription(User user) {
        try {
            Subscription subscription = user.getSubscription();

            accountService.debitAccount(user.getAccount(), subscription.getPrice());

            Status status = statusService.getStatusByName("ACTIVE");
            subscription.setStatus(status);
            subscriptionRepository.save(subscription);
        } catch (InsufficientFundsException e) {
            //TODO log failed activation
            System.out.println(e.getMessage());
        }
    }
}
