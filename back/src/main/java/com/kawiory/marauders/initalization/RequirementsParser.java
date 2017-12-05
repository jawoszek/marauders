package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.city.Requirements;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Kacper
 */

@Component
@Order(2)
public class RequirementsParser {

    private final PairsParser pairsParser;

    public RequirementsParser(PairsParser pairsParser) {
        this.pairsParser = pairsParser;
    }

    public Requirements parse(String requirements) {
        return new Requirements(
                pairsParser.parse(requirements)
        );
    }
}
