package com.cvc.test.selection.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Max(20)
    private final String number;
    @Column(name = "name")
    @NotBlank
    @Max(50)
    private final String name;
    @Column(name = "document")
    @NotBlank
    @Max(20)
    private final String document;

}
