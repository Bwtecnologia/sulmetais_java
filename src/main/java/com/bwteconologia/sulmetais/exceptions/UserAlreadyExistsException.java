package com.bwteconologia.sulmetais.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException {
    private String message;
    public UserAlreadyExistsException(String message){
        this.message = message;
    }
}
