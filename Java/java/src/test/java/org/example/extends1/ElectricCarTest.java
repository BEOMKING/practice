package org.example.extends1;

import org.example.extends1.car.ElectricCar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricCarTest {

    @Test
    void charge() {
        ElectricCar electricCar = new ElectricCar();
        assertEquals("Charging...", electricCar.charge());
    }

    @Test
    void move() {
        ElectricCar electricCar = new ElectricCar();
        assertEquals("Moving...", electricCar.move());
    }

}