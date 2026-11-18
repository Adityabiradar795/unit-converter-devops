package com.example.converter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ConverterController {

    private final ConverterService service;

    public ConverterController(ConverterService service) {
        this.service = service;
    }

    // GET /convert?from=km&to=miles&value=5
    @GetMapping("/convert")
    public Map<String, Object> convert(@RequestParam String from,
                                       @RequestParam String to,
                                       @RequestParam double value) {
        double result = service.convert(from, to, value);
        return Map.of("from", from, "to", to, "value", value, "result", result);
    }

    // GET /units
    @GetMapping("/units")
    public Map<String, List<String>> units() {
        return service.supportedUnits();
    }

    // GET /  (simple root / hello endpoint)
    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of("app", "unit-converter", "status", "running");
    }

    @GetMapping("/hi")
    public Map<String, String> hello(@RequestParam(defaultValue = "DevOps") String name) {
        return Map.of("message", "hi, " + name + "!");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }
}
