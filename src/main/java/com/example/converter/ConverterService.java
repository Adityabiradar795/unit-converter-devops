package com.example.converter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConverterService {

    // Length -> metres, Weight -> kilograms
    private static final Map<String, Double> LENGTH = Map.of(
            "m", 1.0, "km", 1000.0, "cm", 0.01, "miles", 1609.344, "ft", 0.3048);
    private static final Map<String, Double> WEIGHT = Map.of(
            "kg", 1.0, "g", 0.001, "lb", 0.45359237);
    private static final List<String> TEMP = List.of("c", "f", "k");

    public double convert(String from, String to, double value) {
        from = from.toLowerCase();
        to = to.toLowerCase();

        if (LENGTH.containsKey(from) && LENGTH.containsKey(to)) {
            return value * LENGTH.get(from) / LENGTH.get(to);
        }
        if (WEIGHT.containsKey(from) && WEIGHT.containsKey(to)) {
            return value * WEIGHT.get(from) / WEIGHT.get(to);
        }
        if (TEMP.contains(from) && TEMP.contains(to)) {
            return convertTemperature(from, to, value);
        }
        throw new IllegalArgumentException("Unsupported or incompatible units: " + from + " -> " + to);
    }

    private double convertTemperature(String from, String to, double value) {
        double celsius = switch (from) {
            case "f" -> (value - 32) * 5 / 9;
            case "k" -> value - 273.15;
            default -> value;
        };
        return switch (to) {
            case "f" -> celsius * 9 / 5 + 32;
            case "k" -> celsius + 273.15;
            default -> celsius;
        };
    }

    public Map<String, List<String>> supportedUnits() {
        return Map.of(
                "length", LENGTH.keySet().stream().sorted().toList(),
                "weight", WEIGHT.keySet().stream().sorted().toList(),
                "temperature", TEMP);
    }
}
