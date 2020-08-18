package com.vigneshvrt.coffeemachine.core;

import lombok.Getter;

import java.util.Map;

@Getter
public class Beverage {

    private final String name;
    private final Map<String, Integer> ingredients;

    Beverage(String name, Map<String, Integer> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
