package com.example.calculator.rest.parser;

import com.example.calculator.model.Operation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationConverter implements Converter<String, Operation> {

    @Override
    public Operation convert(String value) {
        return Operation.valueOf(value.toUpperCase());
    }
}
