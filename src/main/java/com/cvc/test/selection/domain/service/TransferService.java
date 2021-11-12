package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.Tax;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.exceptions.DateIntervalInvalidException;
import com.cvc.test.selection.domain.exceptions.UndefinedTaxException;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@Service
public class TransferService implements TransferUseCase {

    @Override
    @Transactional
    public void schedule(Transfer transfer) {

        Tax tax = defineTax(transfer.getTransferDate(), transfer.getAmount());
        transfer.setTax(tax);

    }

    private Tax defineTax(LocalDate transferDate, BigDecimal amount){
        if(transferDate.isEqual(LocalDate.now())){
            return Tax.A;
        }
        if(Duration.between(LocalDate.now(), transferDate).toDays() <= 10){
            return Tax.B;
        }
        if(Duration.between(LocalDate.now(), transferDate).toDays() > 10){
            return Tax.C;
        }
        throw new DateIntervalInvalidException("Invalid Date interval");
    }

    private BigDecimal calculateTaxAmount(Tax tax, Transfer transfer){
        Long scheduleAndTransferDaysDifference = Duration.between(transfer.getScheduleDate(), transfer.getTransferDate()).toDays();
        switch (tax){
            case A:
                return BigDecimal.valueOf(3L).add(transfer.getAmount().multiply(BigDecimal.valueOf(0.03)));
            case B:
                return BigDecimal.valueOf(12L).multiply(BigDecimal.valueOf(scheduleAndTransferDaysDifference));
            case C:
                if(scheduleAndTransferDaysDifference > 10 && scheduleAndTransferDaysDifference <= 20){
                    return transfer.getAmount().multiply(BigDecimal.valueOf(0.08));
                }
                if(scheduleAndTransferDaysDifference > 20 && scheduleAndTransferDaysDifference <= 30){
                    return transfer.getAmount().multiply(BigDecimal.valueOf(0.06));
                }
                if(scheduleAndTransferDaysDifference > 30 && scheduleAndTransferDaysDifference <= 40){
                    return transfer.getAmount().multiply(BigDecimal.valueOf(0.04));
                }
                if(scheduleAndTransferDaysDifference > 40 && transfer.getAmount().longValue() > 100000){
                    return transfer.getAmount().multiply(BigDecimal.valueOf(0.02));
                }
        }
        throw new UndefinedTaxException("Undefined tax");
    }
}
