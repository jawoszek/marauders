package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.location.Building;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Kacper
 */

@Component
@Order(2)
public class BuildingsParser {

    private final ModelFilesReader reader;

    public BuildingsParser(ModelFilesReader reader) {
        this.reader = reader;
    }

    public List<Building> parse(String fileName, Function<String[], Building> parseLevelFunction) {
        return reader
                .readFile(fileName)
                .map(parseLevelFunction)
                .collect(Collectors.groupingBy(Building::getName))
                .entrySet()
                .stream()
                .map(
                        levels -> new Building(
                                levels.getKey(),
                                levels.getValue()
                                        .stream()
                                        .map(Building::isCityBuilding)
                                        .findAny()
                                        .orElse(false),
                                levels
                                        .getValue()
                                        .stream()
                                        .flatMap(
                                                building -> building
                                                        .getLevels()
                                                        .entrySet()
                                                        .stream())
                                        .collect(
                                                Collectors.toMap(
                                                        Map.Entry::getKey,
                                                        Map.Entry::getValue
                                                ))

                        )
                ).collect(Collectors.toList());
    }
}
