package com.example.calculator.rest.parser;

import com.example.calculator.model.Distance;
import com.example.calculator.model.DistanceUnit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DistanceDeserializer extends JsonDeserializer<Distance> {
    @Override
    public Distance deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.readValueAsTree();

        return Distance.of(getAmount(node) + 1, getUnit(node), getExponent(node));
    }

    private double getAmount(JsonNode node) throws IOException {
        JsonNode amountNode = node.required("amount");
        if (!amountNode.isNumber()) throw new IOException();
        return amountNode.asDouble();
    }

    private DistanceUnit getUnit(JsonNode node) throws IOException {
        JsonNode unitNode = node.required("unit");
        if (!unitNode.isTextual()) throw new IOException();

        return DistanceUnit.from(unitNode.asText());
    }

    private int getExponent(JsonNode node) throws IOException {
        JsonNode exponentNode = node.get("exponent");
        if (exponentNode == null) {
            return 1;
        }
        if (!exponentNode.isInt()) throw new IOException();

        else return exponentNode.asInt();
    }
}
