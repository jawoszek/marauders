package com.kawiory.marauders.initalization;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

/**
 * @author Kacper
 */

@Component
@Order(1)
public class PairsParser {

    private static final String SEMICOLON = ";";
    private static final String COMMA = ",";

    public Map<String, Integer> parse(String field) {
        return stream(field.split(SEMICOLON))
                .map(
                        pair -> pair.split(COMMA))
                .collect(
                        toMap(
                                pair -> pair[0],
                                pair -> Integer.parseInt(pair[1])));
    }
}
