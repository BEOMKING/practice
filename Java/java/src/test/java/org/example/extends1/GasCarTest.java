package org.example.extends1;

import org.example.extends1.car.GasCar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GasCarTest {

    @Test
    void fillUp() {
        GasCar gasCar = new GasCar();
        assertEquals("Filling up...", gasCar.fillUp());
    }

    @Test
    void move() {
        GasCar gasCar = new GasCar();
        assertEquals("Moving...", gasCar.move());
    }

}