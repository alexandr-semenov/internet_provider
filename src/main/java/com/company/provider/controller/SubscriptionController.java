package com.company.provider.controller;

import com.company.provider.dto.SubscriptionDto;
import com.company.provider.exeption.RestException;
import com.company.provider.service.SubscriptionService;
import com.company.provider.service.TariffService;
import com.company.provider.utils.ApiResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final TariffService tariffService;
    private final ResourceBundleMessageSource messageSource;

    public SubscriptionController(
            SubscriptionService subscriptionService,
            TariffService tariffService,
            ResourceBundleMessageSource messageSource
    ) {
        this.subscriptionService = subscriptionService;
        this.tariffService = tariffService;
        this.messageSource = messageSource;
    }

    @GetMapping("/subscription/{tariff_id}")
    public String subscription(@PathVariable int tariff_id, Model model) {
        model.addAttribute("tariff_id", tariff_id);
        model.addAttribute("tariffsAll", tariffService.loadAllTariffs());

        return "subscription_page";
    }

    @PostMapping("/subscribe")
    public @ResponseBody ResponseEntity<ApiResponse> subscribe(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        try {
            subscriptionService.subscribe(subscriptionDto);
        } catch (Exception e) {
            throw new RestException(e.getMessage(), e.getCause(), false, false);
        }

        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.CREATED, messageSource.getMessage("order_created_message", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
