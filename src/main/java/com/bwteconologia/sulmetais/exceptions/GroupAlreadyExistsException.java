package com.bwteconologia.sulmetais.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GroupAlreadyExistsException  extends RuntimeException{
    private String message;
    public GroupAlreadyExistsException(String message){
        this.message = message;
    }
}
