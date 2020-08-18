package com.vigneshvrt.coffeemachine.core;

import com.vigneshvrt.coffeemachine.core.exception.IngredientInSufficientException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientNotAvailableException;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

class Inventory {

    private ConcurrentHashMap<String, Integer> ingredients = new ConcurrentHashMap<>();

    void load(String ingredient, int quantity) {
        int existingQuantity = ingredients.getOrDefault(ingredient, 0);
        Assert.isTrue((existingQuantity + quantity) >= 0, () -> "quantity underflow");
        ingredients.put(ingredient, ingredients.getOrDefault(ingredient, 0) + quantity);
    }

    void ensureIngredientAvailability(String ingredient) throws IngredientNotAvailableException {
        if (!ingredients.containsKey(ingredient)) {
            throw new IngredientNotAvailableException(ingredient);
        }
    }

    void ensureIngredientSufficiency(String ingredient, int minQuantity) throws IngredientInSufficientException {
        int availableQuantity = ingredients.get(ingredient);
        if (availableQuantity < minQuantity) {
            throw new IngredientInSufficientException(ingredient);
        }
    }

    void consumeIngredient(String ingredient, int quantity) throws IngredientNotAvailableException, IngredientInSufficientException {
        ensureIngredientAvailability(ingredient);
        ensureIngredientSufficiency(ingredient, quantity);
        int availableQuantity = ingredients.get(ingredient);
        ingredients.put(ingredient, availableQuantity - quantity);
    }
}
