package com.cvc.test.selection.domain.exceptions;

public class AccountNotFoundException extends BusinessException{

    public AccountNotFoundException(String message){
        super(message);
    }
}
