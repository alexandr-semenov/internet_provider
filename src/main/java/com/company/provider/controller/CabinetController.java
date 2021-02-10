package com.company.provider.controller;

import com.company.provider.dto.AmountDto;
import com.company.provider.entity.User;
import com.company.provider.service.AccountService;
import com.company.provider.utils.ApiResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class CabinetController {
    private final AccountService accountService;
    private final ResourceBundleMessageSource messageSource;

    public CabinetController(
            AccountService accountService,
            ResourceBundleMessageSource messageSource
    ) {
        this.accountService = accountService;
        this.messageSource = messageSource;
    }

    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        return "cabinet/main";
    }

    @GetMapping("/cabinet/deposit")
    public String depositPage() {
        return "cabinet/deposit";
    }

    @PostMapping("/cabinet/deposit")
    public ResponseEntity<ApiResponse> makeDeposit(@Valid @RequestBody AmountDto amountDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        accountService.depositAccount(user, amountDto.getAmount());

        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.CREATED,  messageSource.getMessage("success_deposit", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
