package com.cvc.test.selection.domain.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tax")
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "amount")
    @NotNull
    @Positive
    @Digits(fraction = 2, integer = 10, message ="msg2")
    private BigDecimal amount;
    @Column(name = "percentage")
    @NotNull
    @Positive
    @Digits(fraction = 2, integer = 4, message ="msg2")
    private BigDecimal percentage;
}
