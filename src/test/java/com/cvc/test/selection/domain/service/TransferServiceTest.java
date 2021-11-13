package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.exceptions.AccountNotFoundException;
import com.cvc.test.selection.domain.exceptions.DateIntervalInvalidException;
import com.cvc.test.selection.domain.exceptions.InvalidTransferException;
import com.cvc.test.selection.domain.exceptions.UndefinedTaxException;
import com.cvc.test.selection.domain.repository.AccountRepository;
import com.cvc.test.selection.domain.repository.TransferRepository;
import com.cvc.test.selection.domain.service.mockfactory.TransferFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TransferServiceTest {

    @MockBean
    private TransferTypeService transferTypeService;
    @MockBean
    private TaxService taxService;
    @MockBean
    private TransferRepository transferRepository;
    @MockBean
    private AccountRepository accountRepository;
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        transferService = new TransferService(transferTypeService,taxService,transferRepository,accountRepository);
    }

    @Test
    public void scheduleWithPastDate() {
        LocalDate currentDate = LocalDate.now();
        Transfer transfer = TransferFactory.createTransferTransferDate(currentDate.minusDays(1),0);
        BDDMockito.given(transferTypeService.defineTransferType(currentDate.minusDays(1))).willThrow(DateIntervalInvalidException.class);
        Assertions.assertThrows(DateIntervalInvalidException.class, () -> transferService.schedule(transfer));
    }

    @Test
    public void scheduleTestInvalidTax() {
        Transfer transfer = TransferFactory.createTransferTransferDate(LocalDate.now(),0);
        LocalDate currentDate = LocalDate.now();
        BDDMockito.given(transferTypeService.defineTransferType(currentDate)).willReturn(TransferType.A);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willThrow(UndefinedTaxException.class);
        Assertions.assertThrows(UndefinedTaxException.class, () -> transferService.schedule(transfer));
    }

    @Test
    public void scheduleSameOriginAndDestination() {
        Transfer transfer = TransferFactory.createTransferSameOriginAndDestination();
        BDDMockito.given(transferTypeService.defineTransferType(transfer.getTransferDate())).willReturn(TransferType.A);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(3.30).setScale(2));
        Assertions.assertThrows(InvalidTransferException.class, () -> transferService.schedule(transfer));
    }

    @Test
    public void scheduleInvalidOrigin() {
        Transfer transfer = TransferFactory.createTransferTransferDate(LocalDate.now(),0);
        BDDMockito.given(transferTypeService.defineTransferType(transfer.getTransferDate())).willReturn(TransferType.A);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(3.30).setScale(2));
        BDDMockito.given(accountRepository.findById(transfer.getOrigin().getNumber())).willReturn(Optional.empty());
        Assertions.assertThrows(AccountNotFoundException.class, () -> transferService.schedule(transfer));
    }

    @Test
    public void scheduleInvalidDestination() {
        Transfer transfer = TransferFactory.createTransferTransferDate(LocalDate.now(),0);
        BDDMockito.given(transferTypeService.defineTransferType(transfer.getTransferDate())).willReturn(TransferType.A);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(3.30).setScale(2));
        BDDMockito.given(accountRepository.findById(transfer.getOrigin().getNumber())).willReturn(Optional.of(transfer.getOrigin()));
        BDDMockito.given(accountRepository.findById(transfer.getDestination().getNumber())).willReturn(Optional.empty());
        Assertions.assertThrows(AccountNotFoundException.class, () -> transferService.schedule(transfer));
    }

    @Test
    public void scheduleCaseA() {
        LocalDate currentDate = LocalDate.now();
        Transfer transfer = TransferFactory.createTransferTransferDate(currentDate,0);

        BDDMockito.given(transferTypeService.defineTransferType(currentDate)).willReturn(TransferType.A);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(3.30).setScale(2));
        BDDMockito.given(accountRepository.findById(transfer.getOrigin().getNumber())).willReturn(Optional.of(transfer.getOrigin()));
        BDDMockito.given(accountRepository.findById(transfer.getDestination().getNumber())).willReturn(Optional.of(transfer.getDestination()));
        BDDMockito.given(transferRepository.save(transfer)).willReturn(transfer);

        Transfer persistedTransfer = transferService.schedule(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(3.30).setScale(2),persistedTransfer.getTaxAmount());
        Assertions.assertEquals(TransferType.A,persistedTransfer.getTransferType());
    }

    @Test
    public void scheduleCaseB() {
        LocalDate currentDate = LocalDate.now().plusDays(1);
        Transfer transfer = TransferFactory.createTransferTransferDate(currentDate,9);

        BDDMockito.given(transferTypeService.defineTransferType(currentDate)).willReturn(TransferType.B);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(108));
        BDDMockito.given(accountRepository.findById(transfer.getOrigin().getNumber())).willReturn(Optional.of(transfer.getOrigin()));
        BDDMockito.given(accountRepository.findById(transfer.getDestination().getNumber())).willReturn(Optional.of(transfer.getDestination()));
        BDDMockito.given(transferRepository.save(transfer)).willReturn(transfer);

        Transfer persistedTransfer = transferService.schedule(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(108),persistedTransfer.getTaxAmount());
        Assertions.assertEquals(TransferType.B,persistedTransfer.getTransferType());
    }

    @Test
    public void scheduleCaseBSameDate() {
        LocalDate currentDate = LocalDate.now().plusDays(1);
        Transfer transfer = TransferFactory.createTransferTransferDate(currentDate,0);

        BDDMockito.given(transferTypeService.defineTransferType(currentDate)).willReturn(TransferType.B);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(12));
        BDDMockito.given(accountRepository.findById(transfer.getOrigin().getNumber())).willReturn(Optional.of(transfer.getOrigin()));
        BDDMockito.given(accountRepository.findById(transfer.getDestination().getNumber())).willReturn(Optional.of(transfer.getDestination()));
        BDDMockito.given(transferRepository.save(transfer)).willReturn(transfer);

        Transfer persistedTransfer = transferService.schedule(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(12),persistedTransfer.getTaxAmount());
        Assertions.assertEquals(TransferType.B,persistedTransfer.getTransferType());
    }

    @Test
    public void scheduleCaseC() {
        LocalDate currentDate = LocalDate.now();
        Transfer transfer = TransferFactory.createTransferTransferDate(currentDate,15);

        BDDMockito.given(transferTypeService.defineTransferType(currentDate)).willReturn(TransferType.C);
        BDDMockito.given(taxService.calculateTaxAmount(transfer)).willReturn(BigDecimal.valueOf(0.80).setScale(2));
        BDDMockito.given(accountRepository.findById(transfer.getOrigin().getNumber())).willReturn(Optional.of(transfer.getOrigin()));
        BDDMockito.given(accountRepository.findById(transfer.getDestination().getNumber())).willReturn(Optional.of(transfer.getDestination()));
        BDDMockito.given(transferRepository.save(transfer)).willReturn(transfer);

        Transfer persistedTransfer = transferService.schedule(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.80).setScale(2),persistedTransfer.getTaxAmount());
        Assertions.assertEquals(TransferType.C,persistedTransfer.getTransferType());
    }
}
