package com.cvc.test.selection.web.controller.mapper;

import com.cvc.test.selection.domain.entity.Account;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.web.controller.dto.TransferDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    public Transfer convertDTOToModel(TransferDTO transferDTO){
        return new Transfer(
                null,
                transferDTO.getAmount(),
                null,
                transferDTO.getTransferDate(),
                transferDTO.getScheduleDate(),
                new Account (transferDTO.getAccountOrigin(),null,null),
                new Account (transferDTO.getAccountDestination(),null,null),
                null
        );
    }

    public TransferDTO convertModelToDTO(Transfer transfer){
        TransferDTO transferDTO =  new TransferDTO(
                transfer.getId(),
                transfer.getAmount(),
                transfer.getTransferDate(),
                transfer.getScheduleDate(),
                transfer.getOrigin().getNumber(),
                transfer.getDestination().getNumber()
        );
        transferDTO.setTaxAmount(transfer.getTaxAmount());
        return transferDTO;
    }
}
