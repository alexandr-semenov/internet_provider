package com.company.provider.entity;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tariff")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "tariff")
    private List<TariffOption> options;

    @ManyToMany(mappedBy = "tariffs")
    private Set<Subscription> subscriptions;

    public Tariff() {
    }

    public Tariff(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Tariff(Builder builder) {
        name = builder.name;
        description = builder.description;
        price = builder.price;
        product = builder.product;
        options = builder.options;
        subscriptions = builder.subscriptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<TariffOption> getOptions() {
        return options;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOptions(List<TariffOption> items) {
        this.options = items;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private Double price;
        private Product product;
        private List<TariffOption> options;
        private Set<Subscription> subscriptions;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setOptions(List<TariffOption> options) {
            this.options = options;
            return this;
        }

        public Builder setSubscriptions(Set<Subscription> subscriptions) {
            this.subscriptions = subscriptions;
            return this;
        }

        public Tariff build() {
            return new Tariff(this);
        }
    }
}
