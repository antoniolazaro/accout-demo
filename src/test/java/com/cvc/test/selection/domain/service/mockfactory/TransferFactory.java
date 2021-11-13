package com.cvc.test.selection.domain.service.mockfactory;

import com.cvc.test.selection.domain.entity.Account;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.web.controller.dto.TransferDTO;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public final class TransferFactory {

    private TransferFactory(){
    }

    public static TransferDTO createTransferDTORequest(){
        return new TransferDTO(null,BigDecimal.TEN,null,LocalDate.now(),LocalDate.now().plusDays(2),AccountFactory.createAccountOrigin().getNumber(),AccountFactory.createAccountDestination().getNumber(),null);
    }

    public static Transfer createTransferDefaultAmount10SemId(){
        return new Transfer(null,BigDecimal.valueOf(10),null,LocalDate.now(),LocalDate.now().plusDays(2),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination(),null);
    }

    public static Transfer createTransferComplete(){
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),BigDecimal.ONE,LocalDate.now(),LocalDate.now().plusDays(2),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination(), TransferType.A);
    }

    public static Transfer createTransferDefaultAmount10(){
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),null,LocalDate.now(),LocalDate.now().plusDays(2),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination(),null);
    }

    public static Transfer createTransferSameOriginAndDestination(){
        Account account = AccountFactory.createAccountOrigin();
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),null,LocalDate.now(),LocalDate.now().plusDays(2),account,account,null);
    }

    public static Transfer createTransferDate(LocalDate transferDate, Integer interval){
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),null,transferDate,transferDate.plusDays(interval),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination(),null);
    }

    public static Transfer createTransferDefaultAmount10IntervalDate(Integer differenceDate){
        LocalDate today = LocalDate.now();
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),null,today,today.plusDays(differenceDate),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination(),null);
    }

    public static Transfer createTransferIntervalDate(Integer differenceDate, BigDecimal amount){
        LocalDate today = LocalDate.now();
        return new Transfer(new Random().nextLong(),amount,null,today,today.plusDays(differenceDate),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination(),null);
    }
}
