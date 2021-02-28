package com.company.provider.controller;

import com.company.provider.entity.Tariff;
import com.company.provider.entity.User;
import com.company.provider.service.ProductService;
import com.company.provider.service.TariffService;
import com.company.provider.service.UserService;
import com.company.provider.utils.ApiResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final TariffService tariffService;
    private final ResourceBundleMessageSource messageSource;

    public AdminController(
            UserService userService,
            ProductService productService,
            TariffService tariffService,
            ResourceBundleMessageSource messageSource
    ) {
        this.userService = userService;
        this.productService = productService;
        this.tariffService = tariffService;
        this.messageSource = messageSource;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/main";
    }

    @GetMapping("/admin/pending-users")
    public String pendingUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Page<User> userPage = userService.loadNotActiveUsersPaginated(page - 1, size);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", userPage);

        return "admin/pending-users";
    }

    @Transactional
    @PatchMapping("/admin/activate-user/{userId}")
    public ResponseEntity<ApiResponse> activateUser(@PathVariable Long userId) {
        User user = userService.loadUserById(userId);
        userService.activateUser(user);

        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.OK,  messageSource.getMessage("username_activated", null, LocaleContextHolder.getLocale())
        );

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("/admin/tariff")
    public String editTariffs(Model model) {
        model.addAttribute("products", productService.findAll());

        return "admin/edit-tariffs";
    }

    @GetMapping("/admin/tariff/create")
    public String createTariffs(Model model) {
        model.addAttribute("products", productService.findAll());

        return "admin/create-tariff";
    }

    @Transactional
    @GetMapping("/admin/tariff/{id}")
    public String createTariffs(Model model, @PathVariable Long id) {
        Tariff tariff = tariffService.loadTariffById(id);

        model.addAttribute("products", productService.findAll());
        model.addAttribute("tariff", tariff);

        return "admin/edit-tariff";
    }
}
