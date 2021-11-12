package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.exceptions.UndefinedTaxException;
import com.cvc.test.selection.domain.service.mockfactory.TransferFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
public class TaxServiceTest {

    private TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = new TaxService();
    }

    @Test
    public void calculateTaxAmountWithTransferTypeNull() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10();
        Assertions.assertThrows(UndefinedTaxException.class, () -> taxService.calculateTaxAmount(transfer));
    }

    @Test
    public void calculateTaxAmountWithTransferTypeA() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10();
        transfer.setTransferType(TransferType.A);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(3.30).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeB() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(9);
        transfer.setTransferType(TransferType.B);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(108),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCLessThen10() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(10);
        transfer.setTransferType(TransferType.C);
        Assertions.assertThrows(UndefinedTaxException.class, () -> taxService.calculateTaxAmount(transfer));
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCGreaterThen10LessOrEqualThen20() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(15);
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.80).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCIntervalEqual20() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(20);
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.80).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCGreaterThen20LessOrEqualThen30() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(25);
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.60).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCIntervalEqual30() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(30);
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.60).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCGreaterThen30LessOrEqualThen40() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(35);
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.40).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCIntervalEqual40() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(40);
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(0.40).setScale(2),taxAmount);
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCGreaterThen40AndLessThen100000() {
        Transfer transfer = TransferFactory.createTransferDefaultAmount10IntervalDate(41);
        transfer.setTransferType(TransferType.C);
        Assertions.assertThrows(UndefinedTaxException.class, () -> taxService.calculateTaxAmount(transfer));
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCGreaterThen40AndEqual100000() {
        Transfer transfer = TransferFactory.createTransferIntervalDate(41,BigDecimal.valueOf(100000));
        transfer.setTransferType(TransferType.C);
        Assertions.assertThrows(UndefinedTaxException.class, () -> taxService.calculateTaxAmount(transfer));
    }

    @Test
    public void calculateTaxAmountWithTransferTypeCGreaterThen40AndGreaterThen100000() {
        Transfer transfer = TransferFactory.createTransferIntervalDate(41,BigDecimal.valueOf(100001));
        transfer.setTransferType(TransferType.C);
        BigDecimal taxAmount = taxService.calculateTaxAmount(transfer);
        Assertions.assertEquals(BigDecimal.valueOf(2000.02).setScale(2),taxAmount);
    }

}
