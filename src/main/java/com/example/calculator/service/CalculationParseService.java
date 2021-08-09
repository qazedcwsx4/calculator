package com.example.calculator.service;

import com.example.calculator.model.Distance;
import com.example.calculator.rest.CalculateRequest;

public interface CalculationParseService {

    /**
     * Parses the supplied expression into a {@link CalculateRequest} instance
     * String containing the request should be formatted as follows:<br>
     * {@code [distance][operator][distance]=[result distance unit]}<br>
     * where {@code [distance]} should be formatted as {@code [amount][unit]^[exponent]}<br>
     * where ^[exponent] is optional <br><br>
     * request should conform to:<br>
     * {@code -?[0-9]+[a-zA-Z]+(\^-?[0-9]+)?[-+*\/]-?[0-9]+[a-zA-Z]+(\^-?[0-9]+)?=[a-zA-Z]+}
     * Example: {@code 1m^2*10nm=m}
     *
     * @param request request to parse
     * @return result {@link Distance} instance
     */
    CalculateRequest parseRequest(String request);
}
