package com.example.calculator.rest;

import com.example.calculator.model.Distance;
import com.example.calculator.model.Operation;
import com.example.calculator.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {

    private final CalculateService calculateService;

    @Autowired
    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @PostMapping("/calculate/{operation}")
    ResponseEntity<CalculateResponse> postCalculate(
            @PathVariable Operation operation,
            @RequestBody CalculateRequest request
    ) {
        request.setOperation(operation);

        try {
            Distance resultDistance = calculateService.calculate(request);
            return ResponseEntity.ok(new CalculateResponse(resultDistance.toString(), resultDistance));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/calculate/{calculation}")
    ResponseEntity<CalculateResponse> getCalculate(
            @PathVariable String calculation
    ) {
        try {
            Distance resultDistance = calculateService.calculate(calculation);
            return ResponseEntity.ok(new CalculateResponse(resultDistance.toString(), resultDistance));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
