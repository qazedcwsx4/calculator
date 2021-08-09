package com.example.calculator.rest;

import com.example.calculator.model.Distance;
import com.example.calculator.model.Operation;
import com.example.calculator.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculationController {

    private final CalculateService calculateService;

    @Autowired
    public CalculationController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @PostMapping("/calculate/{operation}")
    ResponseEntity<CalculateResponse> calculate(
            @PathVariable Operation operation,
            @RequestBody CalculateRequest request
    ) {
        request.setOperation(operation);

        Distance resultDistance = calculateService.calculate(request);

        return ResponseEntity.ok(new CalculateResponse(resultDistance.toString(), resultDistance));
    }
}
