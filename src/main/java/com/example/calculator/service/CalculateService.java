package com.example.calculator.service;

import com.example.calculator.model.Distance;
import com.example.calculator.rest.CalculateRequest;

public interface CalculateService {
    /**
     * Calculates the result of supplied request
     *
     * @param request request to calculate
     * @return result {@link Distance} instance
     */
    Distance calculate(CalculateRequest request);

    /**
     * Calculates the result of supplied expression
     * String containing the request must conform to
     * {@code -?[0-9]+[a-zA-Z]+(\^-?[0-9]+)?[-+*\/]-?[0-9]+[a-zA-Z]+(\^-?[0-9]+)?=[a-zA-Z]+}
     * Example: {@code 1m^2*10nm=m}
     *
     * @param request request to calculate
     * @return result {@link Distance} instance
     */
    Distance calculate(String request);
}
