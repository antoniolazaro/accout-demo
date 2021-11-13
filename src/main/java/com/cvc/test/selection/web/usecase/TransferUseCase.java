package com.cvc.test.selection.web.usecase;

import com.cvc.test.selection.domain.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferUseCase {

    Transfer schedule(Transfer transfer);
    Page<Transfer> listSchedules(Pageable pageable);
}
