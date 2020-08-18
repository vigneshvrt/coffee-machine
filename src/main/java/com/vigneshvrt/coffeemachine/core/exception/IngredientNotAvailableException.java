package com.vigneshvrt.coffeemachine.core.exception;

import lombok.Getter;

@Getter
public class IngredientNotAvailableException extends Exception {

    private String name;

    public IngredientNotAvailableException(String name) {
        super(name + " is not available");
        this.name = name;
    }
}
