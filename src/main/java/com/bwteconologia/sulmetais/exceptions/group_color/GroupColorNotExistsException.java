package com.bwteconologia.sulmetais.exceptions.group_color;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GroupColorNotExistsException  extends RuntimeException{
    private String message;
    public GroupColorNotExistsException(String message){
        this.message = message;
    }
}
