package com.example.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterServiceTest {

    private final ConverterService service = new ConverterService();

    @Test
    void kmToMiles() {
        assertEquals(3.1069, service.convert("km", "miles", 5), 0.001);
    }

    @Test
    void celsiusToFahrenheit() {
        assertEquals(212.0, service.convert("c", "f", 100), 0.0001);
    }

    @Test
    void incompatibleUnitsThrow() {
        assertThrows(IllegalArgumentException.class, () -> service.convert("km", "kg", 5));
    }
}
