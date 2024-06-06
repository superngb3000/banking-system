package com.superngb.bankingsystem.model.account;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestModel {
    @NotNull
    private Long from;
    @NotNull
    private Long to;
    @NotNull
    private BigDecimal amount;
}
