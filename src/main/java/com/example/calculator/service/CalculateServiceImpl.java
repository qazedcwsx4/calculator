package com.example.calculator.service;

import com.example.calculator.model.Distance;
import com.example.calculator.rest.CalculateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateServiceImpl implements CalculateService {

    private final CalculationParseService parseService;

    @Autowired
    public CalculateServiceImpl(CalculationParseService parseService) {
        this.parseService = parseService;
    }

    @Override
    public Distance calculate(CalculateRequest request) {
        return request.getOperation().apply(
                request.getDistance1(),
                request.getDistance2()
        ).to(request.getResultUnit());
    }

    @Override
    public Distance calculate(String request) {
        return calculate(parseService.parseRequest(request));
    }
}
