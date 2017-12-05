package com.kawiory.marauders.game.city;

import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * @author Kacper
 */
public class Requirements {

    private final Map<String, Integer> buildings;

    public Requirements(Map<String, Integer> buildings) {
        this.buildings = buildings;
    }

    public Map<String, Integer> getBuildings() {
        return buildings;
    }

    public static Requirements emptyRequirements() {
        return new Requirements(emptyMap());
    }
}
