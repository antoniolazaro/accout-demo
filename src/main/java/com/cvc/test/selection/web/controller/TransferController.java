package com.cvc.test.selection.web.controller;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.web.controller.dto.TransferDTO;
import com.cvc.test.selection.web.controller.mapper.TransferMapper;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/v1/schedule")
public class TransferController {

    private final TransferUseCase transferUseCase;
    private final TransferMapper transferMapper;

    TransferController(TransferUseCase transferUseCase,TransferMapper transferMapper){
        this.transferUseCase = transferUseCase;
        this.transferMapper = transferMapper;
    }

    @GetMapping
    List<TransferDTO> getAllSchedules(){
        return transferUseCase.listSchedules().stream().map(transferMapper::convertModelToDTO).collect(Collectors.toList());
    }

    @PostMapping
    TransferDTO schedule(@RequestBody TransferDTO transferDTO){
        Transfer model = transferMapper.convertDTOToModel(transferDTO);
        Transfer persistedModel = transferUseCase.schedule(model);
        return transferMapper.convertModelToDTO(persistedModel);
    }

}
