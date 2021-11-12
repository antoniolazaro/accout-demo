package com.cvc.test.selection.domain.service.mockfactory;

import com.cvc.test.selection.domain.entity.Account;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.entity.TransferType;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class AccountFactory {

    private AccountFactory(){
    }

    public static Account createAccountOrigin(){
        return new Account(String.valueOf(new Random().nextLong()),"João Origin","23443253");
    }

    public static Account createAccountDestination(){
        return new Account(String.valueOf(new Random().nextLong()),"Maria Destination","123464");
    }
}
