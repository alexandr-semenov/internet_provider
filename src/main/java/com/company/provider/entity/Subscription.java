package com.company.provider.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subscription_tariff",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id")
    )
    List<Tariff> tariffs;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double price;

    public Subscription() {
    }

    private Subscription (Builder builder) {
        user = builder.user;
        tariffs = builder.tariffs;
        status = builder.status;
        price = builder.price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public enum Status { ACTIVE, NOT_ACTIVE }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User user;
        private List<Tariff> tariffs;
        private Status status;
        private Double price;

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setTariffs(List<Tariff> tariffs) {
            this.tariffs = tariffs;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }
}
