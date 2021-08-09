package com.example.calculator.service;

import com.example.calculator.model.Distance;
import com.example.calculator.rest.CalculateRequest;

public interface CalculateService {
    /**
     * Calculates the supplied requests
     *
     * @param request request to calculate
     * @return result {@link Distance} instance
     */
    Distance calculate(CalculateRequest request);
}
