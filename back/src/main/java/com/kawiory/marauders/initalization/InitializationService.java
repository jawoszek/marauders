package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.army.Unit;
import com.kawiory.marauders.game.location.Building;
import com.kawiory.marauders.game.location.Location;
import com.kawiory.marauders.game.resource.Resource;
import io.vavr.Tuple;
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
    private final CitiesParser citiesParser;

    public InitializationService(EcoBuildingsParser ecoBuildingsParser, OtherBuildingsParser otherBuildingsParser,
                                 ResourcesParser resourcesParser, UnitsParser unitsParser, CitiesParser citiesParser) {
        this.ecoBuildingsParser = ecoBuildingsParser;
        this.otherBuildingsParser = otherBuildingsParser;
        this.resourcesParser = resourcesParser;
        this.unitsParser = unitsParser;
        this.citiesParser = citiesParser;
    }

    public Constants initializeConstants() {
        return new Constants(
                initializeResources(),
                initializeBuildings(),
                initializeUnits()
        );
    }

    public Map<Tuple, Location> initializeLocations(Constants constants) {
        return initializeCities(constants);
    }

    private Map<Tuple, Location> initializeCities(Constants constants) {
        Map<Tuple, Location> cities = citiesParser.parse(constants);
        cities.get(Tuple.of(2,5)).setOwnerName("Rudy");
        cities.get(Tuple.of(4,3)).setOwnerName("Skarbnik");
        return cities;
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
