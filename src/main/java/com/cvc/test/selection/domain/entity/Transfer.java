package com.cvc.test.selection.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private BigDecimal amount;
    @Column(name = "tax_amount")
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private BigDecimal taxAmount;
    @Column(columnDefinition = "DATE", name = "transfer_date")
    @FutureOrPresent
    @NotNull
    private LocalDate transferDate;
    @Column(columnDefinition = "DATE", name = "schedule_date")
    @NotNull
    @FutureOrPresent
    private LocalDate scheduleDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_origin_account")
    private Account origin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_destination_account")
    private Account destination;
    @Column(columnDefinition = "DATE", name = "transfer_type")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

}
