package com.company.provider.entity;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double amount;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
    }

    public Account(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.user = builder.user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Double amount;
        private User user;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
