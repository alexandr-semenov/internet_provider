package com.company.provider.dto;

import javax.validation.constraints.NotEmpty;

public class TariffOptionDto {
    @NotEmpty(message = "tariff_option_name_empty_error")
    private String option;

    public TariffOptionDto() {
    }

    public TariffOptionDto(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
