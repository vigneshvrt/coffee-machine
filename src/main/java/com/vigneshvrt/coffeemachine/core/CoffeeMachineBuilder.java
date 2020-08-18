package com.vigneshvrt.coffeemachine.core;

import java.util.HashMap;
import java.util.Map;

public class CoffeeMachineBuilder {

    private final Inventory inventory = new Inventory();
    private final Map<String, BeverageMaker> beverageMakers = new HashMap<>();

    public CoffeeMachineBuilder loadIngredient(String ingredient, int quantity) {
        this.inventory.load(ingredient, quantity);
        return this;
    }

    public CoffeeMachineBuilder addBeverageMaker(String name, Map<String, Integer> ingredients) {
        Beverage beverage = new Beverage(name, ingredients);
        beverageMakers.put(name, new BeverageMaker(inventory, beverage));
        return this;
    }

    public CoffeeMachine build() {
        return new CoffeeMachine(beverageMakers);
    }
}
