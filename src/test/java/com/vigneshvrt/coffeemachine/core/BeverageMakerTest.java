package com.vigneshvrt.coffeemachine.core;

import com.vigneshvrt.coffeemachine.core.exception.IngredientInSufficientException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeverageMakerTest {

    @Test
    void testPrepareSuccessFully() throws IngredientInSufficientException, IngredientNotAvailableException {
        Inventory inventory = new Inventory();
        inventory.load("sugar", 10);
        inventory.load("milk", 10);
        BeverageMaker beverageMaker = new BeverageMaker(inventory,
                new Beverage("coffee",
                        Map.of("sugar", 5, "milk", 10)
                )
        );
        Assertions.assertEquals("coffee", beverageMaker.make().getName());
    }

    @Test
    void testInsufficientIngredient() {
        Inventory inventory = new Inventory();
        inventory.load("sugar", 2);
        inventory.load("milk", 10);
        BeverageMaker beverageMaker = new BeverageMaker(inventory,
                new Beverage("coffee",
                        Map.of("sugar", 5, "milk", 10)
                )
        );
        IngredientInSufficientException ex1 = Assertions.assertThrows(IngredientInSufficientException.class,
                beverageMaker::make);
        assertEquals("sugar", ex1.getName());
    }

    @Test
    void testNotAvailableIngredient() {
        Inventory inventory = new Inventory();
        inventory.load("sugar", 2);
        inventory.load("milk", 10);
        BeverageMaker beverageMaker = new BeverageMaker(inventory,
                new Beverage("coffee",
                        Map.of("sugar", 5, "milk", 10, "coffee_powder", 5)
                )
        );
        IngredientNotAvailableException ex1 = Assertions.assertThrows(IngredientNotAvailableException.class,
                beverageMaker::make);
        assertEquals("coffee_powder", ex1.getName());
    }
}