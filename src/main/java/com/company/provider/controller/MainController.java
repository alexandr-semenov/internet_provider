package com.company.provider.controller;

import com.company.provider.entity.Product;
import com.company.provider.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final String KEY_INTERNET = "internet";
    private final String KEY_INTERNET_TV = "internet_tv";
    private final String KEY_TELEVISION = "television";
    private final String KEY_TELEPHONY = "telephony";

    private final ProductService productService;

    public MainController(ProductService serviceService) {
        this.productService = serviceService;
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/internet")
    public String internetPage(Model model) {

        Product product = productService.findByName(KEY_INTERNET);
        model.addAttribute("product", product);

        return "internet_page";
    }

    @GetMapping("/internet-tv")
    public String internetTvPage(Model model) {

        Product product = productService.findByName(KEY_INTERNET_TV);
        model.addAttribute("product", product);

        return "internet_tv_page";
    }

    @GetMapping("/television")
    public String televisionPage(Model model) {

        Product product = productService.findByName(KEY_TELEVISION);
        model.addAttribute("product", product);

        return "television_page";
    }

    @GetMapping("/telephony")
    public String telephonyPage(Model model) {

        Product product = productService.findByName(KEY_TELEPHONY);
        model.addAttribute("product", product);

        return "telephony_page";
    }
}
