package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.repository.TransferRepository;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService implements TransferUseCase {

    private final TransferTypeService transferTypeService;
    private final TaxService taxService;
    private final TransferRepository transferRepository;

    TransferService(TransferTypeService transferTypeService, TaxService taxService,TransferRepository transferRepository){
        this.transferRepository = transferRepository;
        this.taxService = taxService;
        this.transferTypeService = transferTypeService;
    }

    @Override
    @Transactional
    public void schedule(Transfer transfer) {
        TransferType transferType = transferTypeService.defineTax(transfer.getTransferDate());
        transfer.setTransferType(transferType);
        transfer.setTaxAmount(taxService.calculateTaxAmount(transfer));
        transferRepository.save(transfer);
    }
}
