package com.vigneshvrt.coffeemachine.core;

import com.vigneshvrt.coffeemachine.core.exception.IngredientInSufficientException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientNotAvailableException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
class BeverageMaker {

    private Beverage beverage;
    private Inventory inventoryRef;

    BeverageMaker(Inventory inventoryRef, Beverage beverage) {
        this.beverage = beverage;
        this.inventoryRef = inventoryRef;
    }

    synchronized Beverage make() throws IngredientInSufficientException, IngredientNotAvailableException {
        for (Map.Entry<String, Integer> en : beverage.getIngredients().entrySet()) {
            String ingredient = en.getKey();
            inventoryRef.ensureIngredientAvailability(ingredient);
        }
        for (Map.Entry<String, Integer> en : beverage.getIngredients().entrySet()) {
            String ingredient = en.getKey();
            int quantity = en.getValue();
            inventoryRef.ensureIngredientSufficiency(ingredient, quantity);
        }
        Map<String, Integer> consumed = new HashMap<>();
        try {
            for (Map.Entry<String, Integer> en : beverage.getIngredients().entrySet()) {
                String ingredient = en.getKey();
                int quantity = en.getValue();
                inventoryRef.consumeIngredient(ingredient, quantity);
                consumed.put(ingredient, quantity);
            }
        } catch (IngredientInSufficientException | IngredientNotAvailableException ex) {
            //restoring...
            consumed.forEach((ingredient, quantity) -> inventoryRef.load(ingredient, quantity));
            throw ex;
        }
        return beverage;
    }
}
