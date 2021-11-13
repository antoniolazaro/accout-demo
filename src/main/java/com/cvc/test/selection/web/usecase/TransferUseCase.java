package com.cvc.test.selection.web.usecase;

import com.cvc.test.selection.domain.entity.Transfer;

import java.util.List;

public interface TransferUseCase {

    Transfer schedule(Transfer transfer);
    List<Transfer> listSchedules();
}
