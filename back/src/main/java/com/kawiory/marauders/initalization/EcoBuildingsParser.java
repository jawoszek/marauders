package com.kawiory.marauders.initalization;

import com.google.common.collect.ImmutableMap;
import com.kawiory.marauders.game.location.Building;
import com.kawiory.marauders.game.location.BuildingLevel;
import com.kawiory.marauders.game.location.Requirements;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kawiory.marauders.game.location.Requirements.emptyRequirements;
import static java.lang.Integer.parseInt;

/**
 * @author Kacper
 */

@Component
@Order(3)
public class EcoBuildingsParser {

    private static final String FILE_NAME = "EcoBuildings";

    private final BuildingsParser buildingsParser;
    private final PairsParser resourceParser;
    private final RequirementsParser requirementsParser;

    public EcoBuildingsParser(BuildingsParser buildingsParser, PairsParser resourceParser,
                              RequirementsParser requirementsParser) {
        this.buildingsParser = buildingsParser;
        this.resourceParser = resourceParser;
        this.requirementsParser = requirementsParser;
    }

    public List<Building> parse() {
        return buildingsParser.parse(FILE_NAME, this::parseLevel);
    }

    private Building parseLevel(String[] line) {
        return new Building(
                line[0],
                true,
                ImmutableMap.of(
                        parseInt(line[1]),
                        new BuildingLevel(
                                parseRequirements(line),
                                resourceParser.parse(line[2]),
                                resourceParser.parse(line[3])
                        )
                )
        );
    }

    private Requirements parseRequirements(String[] line){
        if (line.length < 5){
            return emptyRequirements();
        }
        return requirementsParser.parse(line[4]);
    }
}
