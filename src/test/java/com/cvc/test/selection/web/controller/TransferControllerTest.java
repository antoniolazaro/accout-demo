package com.cvc.test.selection.web.controller;

import com.cvc.test.selection.domain.entity.Transfer;
import com.cvc.test.selection.domain.exceptions.AccountNotFoundException;
import com.cvc.test.selection.domain.exceptions.BusinessException;
import com.cvc.test.selection.domain.service.mockfactory.TransferFactory;
import com.cvc.test.selection.web.usecase.TransferUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferControllerTest {

    @MockBean
    private TransferUseCase transferUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTest(@Autowired MockMvc mvc) throws Exception {

        var pageRequest = PageRequest.of(0,20);
        var list = List.of(TransferFactory.createTransferComplete());

        given(transferUseCase.listSchedules(pageRequest)).willReturn(new PageImpl<>(list,pageRequest,list.size()));

        mvc.perform(get("/v1/schedule")).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    void getEmptyTest(@Autowired MockMvc mvc) throws Exception {

        var pageRequest = PageRequest.of(0,20);
        var list = new ArrayList<Transfer>();

        given(transferUseCase.listSchedules(pageRequest)).willReturn(new PageImpl<>(list,pageRequest,list.size()));

        mvc.perform(get("/v1/schedule")).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void postAccountNotFoundException(@Autowired MockMvc mvc) throws Exception {

       given(transferUseCase.schedule(any())).willThrow(AccountNotFoundException.class);

       var request= TransferFactory.createTransferDTORequest();
       mvc.perform(post("/v1/schedule").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).
               andExpect(status().
                       isNotFound()).
               andExpect(content().contentType("application/json"));
    }

    @Test
    void postBusinessException(@Autowired MockMvc mvc) throws Exception {

        given(transferUseCase.schedule(any())).willThrow(BusinessException.class);
        var request= TransferFactory.createTransferDTORequest();
        mvc.perform(post("/v1/schedule").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isUnprocessableEntity()).andExpect(content().contentType("application/json"));
    }

    @Test
    void postException(@Autowired MockMvc mvc) throws Exception {

        given(transferUseCase.schedule(any())).willThrow(RuntimeException.class);
        var request= TransferFactory.createTransferDTORequest();
        mvc.perform(post("/v1/schedule").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(request))).andExpect(status().is5xxServerError()).andExpect(content().contentType("application/json"));
    }

    @Test
    void postSucess(@Autowired MockMvc mvc) throws Exception {

        given(transferUseCase.schedule(any())).willReturn(TransferFactory.createTransferComplete());
        var request= TransferFactory.createTransferDTORequest();
        mvc.perform(post("/v1/schedule").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.taxAmount").value(BigDecimal.ONE));
    }
}
