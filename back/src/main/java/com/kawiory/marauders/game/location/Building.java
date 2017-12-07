package com.kawiory.marauders.game.location;

import java.util.Map;

/**
 * @author Kacper
 */
public class Building {

    private final String name;
    private final Map<Integer, BuildingLevel> levels;

    public Building(String name, Map<Integer, BuildingLevel> levels) {
        this.name = name;
        this.levels = levels;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, BuildingLevel> getLevels() {
        return levels;
    }
}
