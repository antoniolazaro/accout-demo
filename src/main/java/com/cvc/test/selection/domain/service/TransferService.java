package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.Account;
import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.exceptions.AccountNotFoundException;
import com.cvc.test.selection.domain.exceptions.InvalidTransferException;
import com.cvc.test.selection.domain.repository.AccountRepository;
import com.cvc.test.selection.domain.repository.TransferRepository;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Transfer> listSchedules(Pageable pageable) {
        return transferRepository.findAll(pageable);
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
            throw new AccountNotFoundException("""
                    Invalid account: %s 
                    """.formatted(accountNumber));
        }
    }
}
