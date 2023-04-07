package com.bwteconologia.sulmetais.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObjectModel {
    private Long id;
    private String message;

    public void ResponseObject(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
