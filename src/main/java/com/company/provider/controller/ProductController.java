package com.company.provider.controller;

import com.company.provider.dto.TariffDto;
import com.company.provider.entity.Product;
import com.company.provider.service.ProductService;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductService productService;
    private final ResourceBundleMessageSource messageSource;

    public ProductController(ProductService productService, ResourceBundleMessageSource messageSource) {
        this.productService = productService;
        this.messageSource = messageSource;
    }

    @GetMapping("/product/{productId}")
    public List<TariffDto> getProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);

        return product.getTariffs().stream().map(tariff -> new TariffDto(tariff.getId(),
                messageSource.getMessage(tariff.getName(), null, LocaleContextHolder.getLocale())
        )).collect(Collectors.toList());
    }
}
