package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.army.Unit;
import com.kawiory.marauders.game.location.Requirements;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kawiory.marauders.game.location.Requirements.emptyRequirements;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * @author Kacper
 */

@Component
public class UnitsParser {

    private static final String FILE_NAME = "Units";

    private final ModelFilesReader reader;
    private final RequirementsParser requirementsParser;
    private final PairsParser pairsParser;

    public UnitsParser(ModelFilesReader reader, RequirementsParser requirementsParser, PairsParser pairsParser) {
        this.reader = reader;
        this.requirementsParser = requirementsParser;
        this.pairsParser = pairsParser;
    }

    public List<Unit> parse() {
        return reader
                .readFile(FILE_NAME)
                .map(this::parseUnit)
                .collect(toList());
    }

    private Unit parseUnit(String[] line) {
        List<Integer> stats = stream(
                line[1].split(","))
                .map(Integer::parseInt)
                .collect(
                        toList()
                );

        return new Unit(
                line[0],
                stats.get(0),
                stats.get(1),
                stats.get(2),
                stats.get(3),
                stats.get(4),
                pairsParser.parse(line[3]),
                pairsParser.parse(line[2]),
                parseRequirements(line)
                );
    }

    private Requirements parseRequirements(String[] line){
        if (line.length < 5){
            return emptyRequirements();
        }
        return requirementsParser.parse(line[4]);
    }
}
