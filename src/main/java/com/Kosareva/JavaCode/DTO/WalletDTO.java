package com.Kosareva.JavaCode.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class WalletDTO {
    private UUID id;

    @NotNull
    private String name;

    private Double balance;

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
