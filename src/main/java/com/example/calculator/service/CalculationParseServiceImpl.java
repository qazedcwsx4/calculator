package com.example.calculator.service;

import com.example.calculator.rest.CalculateRequest;
import com.example.calculator.rest.parser.CalculationParser;
import org.springframework.stereotype.Service;

@Service
public class CalculationParseServiceImpl implements CalculationParseService{

    public CalculateRequest parseRequest(String request) {
        CalculationParser parser = new CalculationParser(request);

        return parser.parse();
    }
}
