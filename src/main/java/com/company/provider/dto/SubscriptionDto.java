package com.company.provider.dto;

import javax.validation.constraints.NotEmpty;

import java.util.List;

public class SubscriptionDto {
    private int productId;

    private List<TariffDto> tariffs;

    @NotEmpty(message = "username_empty_error")
    private String username;

    @NotEmpty(message = "password_empty_error")
    private String password;

    public SubscriptionDto() {
    }

    public SubscriptionDto(
            int productId,
            List<TariffDto> tariffs,
            @NotEmpty(message = "username_empty_error") String username,
            @NotEmpty(message = "password_empty_error") String password
    ) {
        this.productId = productId;
        this.tariffs = tariffs;
        this.username = username;
        this.password = password;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<TariffDto> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<TariffDto> tariffs) {
        this.tariffs = tariffs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
