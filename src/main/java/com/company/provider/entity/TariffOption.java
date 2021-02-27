package com.company.provider.entity;

import javax.persistence.*;

@Entity
@Table(name = "tariff_option")
public class TariffOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    public TariffOption() {
    }

    private TariffOption(Builder builder) {
        item = builder.item;
        tariff = builder.tariff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String item;
        private Tariff tariff;

        public Builder setItem(String item) {
            this.item = item;
            return this;
        }

        public Builder setTariff(Tariff tariff) {
            this.tariff = tariff;
            return this;
        }

        public TariffOption build() {
            return new TariffOption(this);
        }
    }
}
