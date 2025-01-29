package com.Kosareva.JavaCode.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Operation")
public class Operation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    @NotNull
    private Wallet wallet;


    @Column(name = "amount")
    @Min(value = 0, message = "Amount should be greater than 0")
    private double amount;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setType(OperationType operationType) {
        this.operationType = operationType;
    }
    public static enum OperationType {
        DEPOSIT,
        WITHDRAW;
    }
}
