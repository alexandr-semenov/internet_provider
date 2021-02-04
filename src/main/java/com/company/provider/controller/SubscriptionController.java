package com.company.provider.controller;

import com.company.provider.config.RestException;
import com.company.provider.dto.SubscriptionDto;
import com.company.provider.entity.Product;
import com.company.provider.service.ProductService;
import com.company.provider.service.SubscriptionService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    Map<String, String> subscribe(@RequestBody SubscriptionDto subscriptionDto) {
        try {
            subscriptionService.subscribe(subscriptionDto);
        } catch (Exception e) {
            throw new RestException(e.getMessage(), e.getCause(), false, false);
        }

        Map<String, String> response = new HashMap<>() {{
            put("status", messageSource.getMessage("order_created_message", null, LocaleContextHolder.getLocale()));
        }};
        return response;
    }
}
