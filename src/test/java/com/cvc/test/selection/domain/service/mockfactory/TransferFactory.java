package com.cvc.test.selection.domain.service.mockfactory;

import com.cvc.test.selection.domain.entity.Transfer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public final class TransferFactory {

    private TransferFactory(){
    }

    public static Transfer createTransferDefaultAmount10(){
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),LocalDate.now(),LocalDate.now().plusDays(2),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination());
    }

    public static Transfer createTransferDefaultAmount10IntervalDate(Integer differenceDate){
        LocalDate today = LocalDate.now();
        return new Transfer(new Random().nextLong(),BigDecimal.valueOf(10),today,today.plusDays(differenceDate),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination());
    }

    public static Transfer createTransferIntervalDate(Integer differenceDate, BigDecimal amount){
        LocalDate today = LocalDate.now();
        return new Transfer(new Random().nextLong(),amount,today,today.plusDays(differenceDate),AccountFactory.createAccountOrigin(),AccountFactory.createAccountDestination());
    }
}
