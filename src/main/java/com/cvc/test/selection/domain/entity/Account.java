package com.cvc.test.selection.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Max(20)
    private String number;
    @Column(name = "name")
    @NotBlank
    @Max(50)
    private String name;
    @Column(name = "document")
    @NotBlank
    @Max(20)
    private String document;

}
