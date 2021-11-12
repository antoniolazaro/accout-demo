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
    private final Long id;
    @Column(name = "amount")
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private final BigDecimal amount;
    @Column(name = "tax_amout")
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private BigDecimal taxAmount;
    @Column(columnDefinition = "DATE", name = "transfer_date")
    @FutureOrPresent
    @NotNull
    private final LocalDate transferDate;
    @Column(columnDefinition = "DATE", name = "schedule_date")
    @NotNull
    @FutureOrPresent
    private final LocalDate scheduleDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_origin_account")
    private final Account origin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_destination_account")
    private final Account destination;
    @Column(columnDefinition = "DATE", name = "transfer_type")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

}
