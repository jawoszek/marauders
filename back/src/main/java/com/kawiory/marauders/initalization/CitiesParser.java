package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.location.Building;
import com.kawiory.marauders.game.location.Location;
import io.vavr.Tuple;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.kawiory.marauders.game.location.Location.CITY_TYPE;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toMap;

/**
 * @author Kacper
 */

@Component
public class CitiesParser {

    private static final String FILE_NAME = "Cities";

    private final ModelFilesReader reader;

    public CitiesParser(ModelFilesReader reader) {
        this.reader = reader;
    }

    public Map<Tuple, Location> parse(Constants constants) {
        return reader
                .readFile(FILE_NAME)
                .map(array ->
                        array[0].split(","))
                .map(pair ->
                        Tuple.of(parseInt(pair[0]), parseInt( pair[1])))
                .collect(
                        toMap(
                                pair -> pair,
                                pair -> new Location(CITY_TYPE, getCityInitialBuildings(constants))
                        )
                );
    }

    private Map<String, Integer> getCityInitialBuildings(Constants constants) {
        return constants
                .getBuildings()
                .values()
                .stream()
                .filter(Building::isCityBuilding)
                .collect(
                        toMap(
                                Building::getName,
                                building -> 0
                        )
                );

    }
}
