package com.bwteconologia.sulmetais.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PaymentConditionNotFoundException extends RuntimeException{
    private String message;
    public PaymentConditionNotFoundException(String message){
        this.message = message;
    }
}
