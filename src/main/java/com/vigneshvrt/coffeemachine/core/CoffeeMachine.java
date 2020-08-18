package com.vigneshvrt.coffeemachine.core;

import com.vigneshvrt.coffeemachine.core.exception.CannotPrepareBeverageException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientInSufficientException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientNotAvailableException;

import java.util.Map;

public class CoffeeMachine {

    private final Map<String, BeverageMaker> beverageMakers;

    CoffeeMachine(Map<String, BeverageMaker> beverageMakers) {
        this.beverageMakers = beverageMakers;
    }

    public Beverage prepareBeverage(String name) throws CannotPrepareBeverageException {
        if (!beverageMakers.containsKey(name)) {
            System.err.println("Not Supported Beverage - " + name);
            throw new CannotPrepareBeverageException("Not Supported Beverage");
        }
        try {
            Beverage beverage = beverageMakers.get(name).make();
            System.out.println(name + " is prepared");
            return beverage;
        } catch (IngredientInSufficientException | IngredientNotAvailableException ex) {
            String errMsg = name + " cannot be prepared because " + ex.getMessage();
            System.err.println(errMsg);
            throw new CannotPrepareBeverageException(errMsg, ex);
        }
    }
}
