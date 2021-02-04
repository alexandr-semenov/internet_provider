package com.company.provider.dto;

public class SubscriptionDto {
    private int productId;

    private Long tariffId;

    private String username;

    private String password;

    public SubscriptionDto() {
    }

    public SubscriptionDto(int productId, Long tariffId, String username, String password) {
        this.productId = productId;
        this.tariffId = tariffId;
        this.username = username;
        this.password = password;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Long getTariffId() {
        return tariffId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
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
