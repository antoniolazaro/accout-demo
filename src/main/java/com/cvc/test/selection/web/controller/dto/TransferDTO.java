package com.cvc.test.selection.web.controller.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    private Long id;
    @NotNull
    private BigDecimal amount;
    private BigDecimal taxAmount;
    @FutureOrPresent
    private LocalDate transferDate;
    @FutureOrPresent
    private LocalDate scheduleDate;
    @NotBlank
    private String accountOrigin;
    @NotBlank
    private String accountDestination;
    private String language;
}
