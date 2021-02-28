package com.company.provider.controller;

import com.company.provider.dto.TariffDto;
import com.company.provider.entity.Tariff;
import com.company.provider.service.TariffService;
import com.company.provider.utils.ApiResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TariffController {
    private final TariffService tariffService;
    private final ResourceBundleMessageSource messageSource;

    public TariffController(TariffService tariffService, ResourceBundleMessageSource messageSource) {
        this.tariffService = tariffService;
        this.messageSource = messageSource;
    }

    @PostMapping("/admin/tariff")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody TariffDto tariffDto) {
        tariffService.createTariff(tariffDto);

        ApiResponse response = new ApiResponse(
                HttpStatus.CREATED, messageSource.getMessage("new_tariff_created", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/admin/tariff/{id}")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody TariffDto tariffDto, @PathVariable Long id) {
        Tariff tariff = tariffService.loadTariffById(id);
        tariffService.updateTariff(tariffDto, tariff);

        ApiResponse response = new ApiResponse(
                HttpStatus.CREATED, messageSource.getMessage("tariff_updated", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/admin/tariff/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        Tariff tariff = tariffService.loadTariffById(id);
        tariffService.deleteTariff(tariff);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK, messageSource.getMessage("tariff_deleted", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(response, response.getStatus());
    }
}
