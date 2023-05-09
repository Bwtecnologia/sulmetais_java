package com.bwteconologia.sulmetais.exceptions.group_color;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GroupColorNotExistsException extends RuntimeException {
    private String message;
    public GroupColorNotExistsException() {
        this.message = "This Group Color doesnt exist!";
    }
}
