package com.Kosareva.JavaCode.DTO;

import com.Kosareva.JavaCode.models.Operation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class OperationDTO {

    @NotNull(message = "can not be empty")
    private UUID valletId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "can not be empty")
    private Operation.OperationType operationType;

    @Min(value = 0, message = "should be greater than 0")
    private double amount;

    public UUID getValletId() {
        return valletId;
    }

    public Operation.OperationType getOperationType() {
        return operationType;
    }
    public double getAmount() {
        return amount;
    }
    public void setValletId(UUID valletId) {
        this.valletId = valletId;
    }
    public void setOperationType(Operation.OperationType operationType) {
        this.operationType = operationType;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
