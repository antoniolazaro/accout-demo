package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.exceptions.DateIntervalInvalidException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class TransferTypeService {

    public TransferType defineTax(LocalDate transferDate){
        if(transferDate.isEqual(LocalDate.now())){
            return TransferType.A;
        }
        if(Duration.between(LocalDate.now(), transferDate).toDays() <= 10){
            return TransferType.B;
        }
        if(Duration.between(LocalDate.now(), transferDate).toDays() > 10){
            return TransferType.C;
        }
        throw new DateIntervalInvalidException("Invalid Date interval");
    }
}
