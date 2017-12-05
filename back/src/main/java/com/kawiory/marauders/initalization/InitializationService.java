package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.army.Unit;
import com.kawiory.marauders.game.city.Building;
import com.kawiory.marauders.game.resource.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kacper
 */

@Service
@Order(4)
public class InitializationService {

    private final EcoBuildingsParser ecoBuildingsParser;
    private final OtherBuildingsParser otherBuildingsParser;
    private final ResourcesParser resourcesParser;
    private final UnitsParser unitsParser;

    public InitializationService(EcoBuildingsParser ecoBuildingsParser, OtherBuildingsParser otherBuildingsParser,
                                 ResourcesParser resourcesParser, UnitsParser unitsParser) {
        this.ecoBuildingsParser = ecoBuildingsParser;
        this.otherBuildingsParser = otherBuildingsParser;
        this.resourcesParser = resourcesParser;
        this.unitsParser = unitsParser;
    }

    public Constants initializeContants() {
        return new Constants(
                initializeResources(),
                initializeBuildings(),
                initializeUnits()
        );
    }

    private Map<String, Building> initializeBuildings() {
        return
                Stream.of(
                        ecoBuildingsParser.parse(),
                        otherBuildingsParser.parse()
                )
                        .flatMap(Collection::stream)
                        .collect(Collectors.toMap(
                                Building::getName,
                                building -> building
                        ));
    }

    private Map<String, Resource> initializeResources() {
        return resourcesParser
                .parse()
                .stream()
                .collect(
                        Collectors.toMap(
                                Resource::getName,
                                resource -> resource
                        )
                );
    }

    private Map<String, Unit> initializeUnits(){
        return unitsParser
                .parse()
                .stream()
                .collect(
                        Collectors.toMap(
                                Unit::getName,
                                unit -> unit
                        )
                );
    }
}
