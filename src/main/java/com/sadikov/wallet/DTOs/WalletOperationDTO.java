package com.sadikov.wallet.DTOs;

import com.sadikov.wallet.model.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
public class WalletOperationDTO {
    @NotEmpty
    @UUID
    String walletId;

    @NotEmpty
    OperationType operationType;

    @NotEmpty
    @Min(0)
    int amount;
}
