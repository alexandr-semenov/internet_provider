package com.company.provider.controller;

import com.company.provider.dto.TariffOptionDto;
import com.company.provider.entity.Tariff;
import com.company.provider.entity.TariffOption;
import com.company.provider.service.TariffOptionService;
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
public class TariffOptionController {
    private final TariffOptionService tariffOptionService;
    private final TariffService tariffService;
    private final ResourceBundleMessageSource messageSource;

    public TariffOptionController(
            TariffOptionService tariffItemService,
            TariffService tariffService,
            ResourceBundleMessageSource messageSource
    ) {
        this.tariffOptionService = tariffItemService;
        this.tariffService = tariffService;
        this.messageSource = messageSource;
    }

    @PostMapping("/admin/tariff/option/{id}")
    public ResponseEntity<ApiResponse> create(@PathVariable Long id, @Valid @RequestBody TariffOptionDto tariffOptionDto) {
        Tariff tariff = tariffService.loadTariffById(id);
        tariffOptionService.createTariffOptions(tariffOptionDto, tariff);

        ApiResponse response = new ApiResponse(
                HttpStatus.CREATED, messageSource.getMessage("tariff_option_created", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/admin/tariff/option/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody TariffOptionDto tariffOptionDto) {
        TariffOption tariffOption = tariffOptionService.loadTariffOptionById(id);
        tariffOptionService.updateTariffOption(tariffOption, tariffOptionDto);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK, messageSource.getMessage("tariff_option_updated", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/admin/tariff/option/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        TariffOption tariffOption = tariffOptionService.loadTariffOptionById(id);
        tariffOptionService.deleteTariffOption(tariffOption);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK, messageSource.getMessage("tariff_option_deleted", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(response, response.getStatus());
    }
}
