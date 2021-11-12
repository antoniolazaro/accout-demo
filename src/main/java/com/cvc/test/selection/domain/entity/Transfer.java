package com.cvc.test.selection.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "amount")
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private BigDecimal amount;
    @Column(name = "tax_amout")
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private BigDecimal taxAmount;
    @Column(columnDefinition = "DATE", name = "transferDate")
    @FutureOrPresent
    @NotNull
    private LocalDate transferDate;
    @Column(columnDefinition = "DATE", name = "scheduleDate")
    @NotNull
    @FutureOrPresent
    private LocalDate scheduleDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_origin_account")
    private Account origin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_destination_account")
    private Account destination;
    @Enumerated(EnumType.STRING)
    private Tax tax;

}
