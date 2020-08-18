package com.vigneshvrt.coffeemachine.core.exception;

import lombok.Getter;

@Getter
public class IngredientInSufficientException extends Exception {

    private String name;

    public IngredientInSufficientException(String name) {
        super(name + " is not sufficient");
        this.name = name;
    }
}
