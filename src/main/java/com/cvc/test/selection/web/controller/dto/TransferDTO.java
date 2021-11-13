package com.cvc.test.selection.web.controller.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransferDTO {
    private final Long id;
    @NotNull
    private final BigDecimal amount;
    private BigDecimal taxAmount;
    @FutureOrPresent
    private final LocalDate transferDate;
    @FutureOrPresent
    private final LocalDate scheduleDate;
    @NotBlank
    private final String accountOrigin;
    @NotBlank
    private final String accountDestination;
}
