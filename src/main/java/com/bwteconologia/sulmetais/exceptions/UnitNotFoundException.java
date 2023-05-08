package com.bwteconologia.sulmetais.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnitNotFoundException extends RuntimeException {
    public UnitNotFoundException(String message){
        super(message);
    }
    public UnitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
