package com.vigneshvrt.coffeemachine;

import com.vigneshvrt.coffeemachine.core.Beverage;
import com.vigneshvrt.coffeemachine.core.CoffeeMachine;
import com.vigneshvrt.coffeemachine.core.CoffeeMachineBuilder;
import com.vigneshvrt.coffeemachine.core.exception.CannotPrepareBeverageException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientInSufficientException;
import com.vigneshvrt.coffeemachine.core.exception.IngredientNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CoffeeMachineApplicationTests {

    private CoffeeMachine machine;

    @BeforeEach
    void setUp() {
        CoffeeMachineBuilder machineBuilder = new CoffeeMachineBuilder()
                .loadIngredient("hot_water", 500)
                .loadIngredient("hot_milk", 500)
                .loadIngredient("ginger_syrup", 100)
                .loadIngredient("sugar_syrup", 100)
                .loadIngredient("tea_leaves_syrup", 100)
                .addBeverageMaker(
                        "hot_tea",
                        Map.of(
                                "hot_water", 200,
                                "hot_milk", 100,
                                "ginger_syrup", 10,
                                "sugar_syrup", 10,
                                "tea_leaves_syrup", 10
                        )
                )
                .addBeverageMaker(
                        "hot_coffee",
                        Map.of(
                                "hot_water", 100,
                                "ginger_syrup", 30,
                                "hot_milk", 400,
                                "sugar_syrup", 50,
                                "tea_leaves_syrup", 30
                        )
                )
                .addBeverageMaker(
                        "black_tea",
                        Map.of(
                                "hot_water", 300,
                                "ginger_syrup", 30,
                                "sugar_syrup", 50,
                                "tea_leaves_syrup", 30
                        )
                ).addBeverageMaker(
                        "green_tea",
                        Map.of(
                                "hot_water", 100,
                                "ginger_syrup", 30,
                                "sugar_syrup", 50,
                                "green_mixture", 30
                        )
                ).addBeverageMaker(
                        "ginger_tea",
                        Map.of("hot_water", 5, "ginger_syrup", 5)
                )
                .addBeverageMaker(
                        "masala_tea",
                        Map.of("hot_water", 5, "tea_leaves_syrup", 5)
                );
        this.machine = machineBuilder.build();
    }

    @Test
    void test1() throws CannotPrepareBeverageException {
        Beverage hotTea = machine.prepareBeverage("hot_tea");
        assertEquals("hot_tea", hotTea.getName());

        Beverage hotCoffee = machine.prepareBeverage("hot_coffee");
        assertEquals("hot_coffee", hotCoffee.getName());

        CannotPrepareBeverageException ex = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("green_tea"));
        assertEquals("green_mixture", ((IngredientNotAvailableException) ex.getCause()).getName());

        CannotPrepareBeverageException ex1 = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("black_tea"));
        assertEquals("hot_water", ((IngredientInSufficientException) ex1.getCause()).getName());
    }

    @Test
    void test2() throws CannotPrepareBeverageException {
        Beverage hotTea = machine.prepareBeverage("hot_tea");
        assertEquals("hot_tea", hotTea.getName());

        Beverage blackTea = machine.prepareBeverage("black_tea");
        assertEquals("black_tea", blackTea.getName());

        CannotPrepareBeverageException ex = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("green_tea"));
        assertEquals("green_mixture", ((IngredientNotAvailableException) ex.getCause()).getName());

        CannotPrepareBeverageException ex1 = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("hot_coffee"));
        assertEquals("sugar_syrup", ((IngredientInSufficientException) ex1.getCause()).getName());
    }

    @Test
    void test3() throws CannotPrepareBeverageException {
        Beverage hotCoffee = machine.prepareBeverage("hot_coffee");
        assertEquals("hot_coffee", hotCoffee.getName());

        Beverage blackTea = machine.prepareBeverage("black_tea");
        assertEquals("black_tea", blackTea.getName());

        CannotPrepareBeverageException ex = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("green_tea"));
        assertEquals("green_mixture", ((IngredientNotAvailableException) ex.getCause()).getName());

        CannotPrepareBeverageException ex1 = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("hot_tea"));
        assertEquals("sugar_syrup", ((IngredientInSufficientException) ex1.getCause()).getName());
    }

    @Test
    void testNotSupportedBeverageRequest() {
        CannotPrepareBeverageException ex = Assertions.assertThrows(CannotPrepareBeverageException.class,
                () -> machine.prepareBeverage("cold_coffee"));
        assertTrue(ex.getMessage().contains("Not Supported Beverage"));
    }
}
