package com.cvc.test.selection.web.usecase;

import com.cvc.test.selection.domain.entity.Transfer;

public interface TransferUseCase {

    void schedule(Transfer transfer);
}
