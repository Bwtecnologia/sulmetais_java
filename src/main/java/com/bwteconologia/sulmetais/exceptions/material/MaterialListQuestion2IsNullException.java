package com.bwteconologia.sulmetais.exceptions.material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MaterialListQuestion2IsNullException extends RuntimeException{
    private String message;
}
