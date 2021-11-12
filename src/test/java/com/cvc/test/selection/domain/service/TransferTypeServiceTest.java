package com.cvc.test.selection.domain.service;

import com.cvc.test.selection.domain.entity.TransferType;
import com.cvc.test.selection.domain.exceptions.DateIntervalInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
public class TransferTypeServiceTest {

    private TransferTypeService transferTypeService;

    @BeforeEach
    void setUp() {
        transferTypeService = new TransferTypeService();
    }

    @Test
    public void defineTaxDateIntervalInvalidException() {
        Assertions.assertThrows(DateIntervalInvalidException.class, () -> transferTypeService.defineTransferType(LocalDate.now().plusDays(-1)));
    }

    @Test
    public void defineTaxTypeA() {
        Assertions.assertEquals(TransferType.A,transferTypeService.defineTransferType(LocalDate.now()));
    }

    @Test
    public void defineTaxTypeB() {
        Assertions.assertEquals(TransferType.B,transferTypeService.defineTransferType(LocalDate.now().plusDays(1)));
    }

    @Test
    public void defineTaxTypeBLimit10() {
        Assertions.assertEquals(TransferType.B,transferTypeService.defineTransferType(LocalDate.now().plusDays(10)));
    }

    @Test
    public void defineTaxTypeC() {
        Assertions.assertEquals(TransferType.C,transferTypeService.defineTransferType(LocalDate.now().plusDays(11)));
    }
}
