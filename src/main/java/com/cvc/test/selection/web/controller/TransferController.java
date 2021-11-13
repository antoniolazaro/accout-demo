package com.cvc.test.selection.web.controller;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.web.controller.dto.TransferDTO;
import com.cvc.test.selection.web.controller.mapper.TransferMapper;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/v1/schedule")
public class TransferController {

    Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final TransferUseCase transferUseCase;
    private final TransferMapper transferMapper;

    TransferController(TransferUseCase transferUseCase,TransferMapper transferMapper){
        this.transferUseCase = transferUseCase;
        this.transferMapper = transferMapper;
    }

    @GetMapping
    Page<TransferDTO> getAllSchedules(Pageable pageable){
        logger.info("Searching schedules...");
        List<TransferDTO> listSchedules = transferUseCase.listSchedules(pageable).stream().toList().stream().map(transferMapper::convertModelToDTO).collect(Collectors.toList());
        logger.info("Return schedules %s".formatted(listSchedules.size()));
        return  new PageImpl<>(listSchedules,pageable,listSchedules.size());
    }

    @PostMapping
    TransferDTO schedule(@RequestBody TransferDTO transferDTO){
        logger.info("Saving schedule...");
        Transfer model = transferMapper.convertDTOToModel(transferDTO);
        logger.info("Model converted...");
        Transfer persistedModel = transferUseCase.schedule(model);
        logger.info("Model persistedModel %s...",persistedModel);
        return transferMapper.convertModelToDTO(persistedModel);
    }

}
