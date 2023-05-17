package com.bwteconologia.sulmetais.exceptions.materialbudget;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MaterialOperandNotExistsException extends RuntimeException{
    private String message;
    public MaterialOperandNotExistsException(String message){
        this.message = message;
    }
}
