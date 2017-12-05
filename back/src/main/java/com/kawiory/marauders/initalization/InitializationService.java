package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.city.Building;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
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

    public InitializationService(EcoBuildingsParser ecoBuildingsParser, OtherBuildingsParser otherBuildingsParser) {
        this.ecoBuildingsParser = ecoBuildingsParser;
        this.otherBuildingsParser = otherBuildingsParser;
    }

    public Constants initializeContants() {
        return new Constants(
                Collections.emptyMap(),
                initializeBuildings(),
                Collections.emptyMap()
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
}
