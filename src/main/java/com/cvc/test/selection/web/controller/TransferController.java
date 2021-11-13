package com.cvc.test.selection.web.controller;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.web.controller.dto.TransferDTO;
import com.cvc.test.selection.web.controller.mapper.TransferMapper;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/schedule")
public class TransferController {

    Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final TransferUseCase transferUseCase;
    private final TransferMapper transferMapper;
    private final MessageSource messageResource;

    TransferController(TransferUseCase transferUseCase,TransferMapper transferMapper,MessageSource messageResource){
        this.transferUseCase = transferUseCase;
        this.transferMapper = transferMapper;
        this.messageResource = messageResource;
    }

    @GetMapping
    Page<TransferDTO> getAllSchedules(@RequestParam(value = "page",defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size,
                                      @RequestParam(value = "lang", defaultValue = "US") String lang){
        var pageable = PageRequest.of(page, size);
        logger.info("Searching schedules...");
        String fileContent = messageResource.getMessage("country",null,new Locale(lang));
        List<TransferDTO> listSchedules = transferUseCase.listSchedules(pageable).stream().toList().stream().map(it ->{
            var dto = transferMapper.convertModelToDTO(it);
            dto.setLanguage(fileContent);
            return dto;
        }).collect(Collectors.toList());
        logger.info("Return schedules %s".formatted(listSchedules.size()));
        return  new PageImpl<>(listSchedules,pageable,listSchedules.size());
    }

    @PostMapping
    TransferDTO schedule(@RequestBody TransferDTO transferDTO){
        logger.info("Saving schedule...");
        Transfer model = transferMapper.convertDTOToModel(transferDTO);
        logger.info("Model converted...");
        Transfer persistedModel = transferUseCase.schedule(model);
        logger.info("Model persistedModel %s...".formatted(persistedModel));
        return transferMapper.convertModelToDTO(persistedModel);
    }

}
