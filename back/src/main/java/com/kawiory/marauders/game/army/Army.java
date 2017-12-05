package com.kawiory.marauders.game.army;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class Army {

    private final Map<String, Integer> units;

    public Army() {
        this.units = new HashMap<>();
    }

    public Map<String, Integer> getUnits() {
        return units;
    }
}
