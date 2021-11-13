package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.exceptions.UndefinedTaxException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
public class TaxService {

    public BigDecimal calculateTaxAmount(Transfer transfer){
        long scheduleAndTransferDaysDifference = Duration.between(transfer.getTransferDate().atStartOfDay(), transfer.getScheduleDate().atStartOfDay()).toDays();
        if (transfer.getTransferType() != null) {
            switch (transfer.getTransferType()) {
                case A:
                    return BigDecimal.valueOf(3L).add(transfer.getAmount().multiply(BigDecimal.valueOf(0.03)));
                case B:
                    if(scheduleAndTransferDaysDifference == 0){
                        return BigDecimal.valueOf(12L);
                    }
                    return BigDecimal.valueOf(12L).multiply(BigDecimal.valueOf(scheduleAndTransferDaysDifference));
                case C:
                    if (scheduleAndTransferDaysDifference > 10 && scheduleAndTransferDaysDifference <= 20) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.08));
                    }
                    if (scheduleAndTransferDaysDifference > 20 && scheduleAndTransferDaysDifference <= 30) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.06));
                    }
                    if (scheduleAndTransferDaysDifference > 30 && scheduleAndTransferDaysDifference <= 40) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.04));
                    }
                    if (scheduleAndTransferDaysDifference > 40 && transfer.getAmount().longValue() > 100000) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.02));
                    }
            }
        }
        throw new UndefinedTaxException("Undefined tax");
    }
}
