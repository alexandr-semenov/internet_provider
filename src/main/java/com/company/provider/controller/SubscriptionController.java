package com.company.provider.controller;

import com.company.provider.exeption.RestException;
import com.company.provider.dto.SubscriptionDto;
import com.company.provider.entity.Product;
import com.company.provider.service.ProductService;
import com.company.provider.service.SubscriptionService;
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
    private final ProductService productService;
    private SubscriptionService subscriptionService;
    private ResourceBundleMessageSource messageSource;

    public SubscriptionController(
            ProductService productService,
            SubscriptionService subscriptionService,
            ResourceBundleMessageSource messageSource
    ) {
        this.productService = productService;
        this.subscriptionService = subscriptionService;
        this.messageSource = messageSource;
    }

    @GetMapping("/subscription/{product_id}/{tariff_id}")
    public String subscription(@PathVariable Long product_id, @PathVariable int tariff_id, Model model) {
        Product product = productService.getProduct(product_id);

        model.addAttribute("product_id", product_id);
        model.addAttribute("tariff_id", tariff_id);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("tariffs", product.getTariffs());

        return "subscription_page";
    }

    @PostMapping("/subscribe")
    public @ResponseBody
    ResponseEntity<ApiResponse> subscribe(@Valid @RequestBody SubscriptionDto subscriptionDto) {
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
