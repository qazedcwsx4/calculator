package com.example.calculator.service;

import com.example.calculator.model.Distance;
import com.example.calculator.rest.CalculateRequest;
import org.springframework.stereotype.Service;

@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public Distance calculate(CalculateRequest request) {
        return request.getOperation().apply(
                request.getDistance1(),
                request.getDistance2()
        ).to(request.getResultUnit());
    }
}
