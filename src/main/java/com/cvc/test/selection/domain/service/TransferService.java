package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.Account;
import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.exceptions.AccountNotFound;
import com.cvc.test.selection.domain.exceptions.InvalidTransferException;
import com.cvc.test.selection.domain.repository.AccountRepository;
import com.cvc.test.selection.domain.repository.TransferRepository;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransferService implements TransferUseCase {

    private final TransferTypeService transferTypeService;
    private final TaxService taxService;
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    TransferService(TransferTypeService transferTypeService, TaxService taxService,TransferRepository transferRepository,AccountRepository accountRepository){
        this.transferRepository = transferRepository;
        this.taxService = taxService;
        this.transferTypeService = transferTypeService;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Transfer> listSchedules() {
        return transferRepository.findAll();
    }

    @Override
    @Transactional
    public Transfer schedule(Transfer transfer) {
        TransferType transferType = transferTypeService.defineTransferType(transfer.getTransferDate());
        transfer.setTransferType(transferType);
        transfer.setTaxAmount(taxService.calculateTaxAmount(transfer));
        if(!transfer.getOrigin().getNumber().equals(transfer.getDestination().getNumber())){
            this.validateAccount(transfer.getOrigin());
            this.validateAccount(transfer.getDestination());
            return transferRepository.save(transfer);
        }
        throw new InvalidTransferException("Transfer should have different origin and destination");
    }

    private void validateAccount(Account account){
        String accountNumber = account.getNumber();
        if(!accountRepository.findById(accountNumber).isPresent()){
            throw new AccountNotFound("""
                    Invalid account: %s 
                    """.formatted(accountNumber));
        }
    }
}
