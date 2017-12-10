package com.kawiory.marauders.game.location;

import java.util.Map;

/**
 * @author Kacper
 */
public class Building {

    private final String name;
    private final boolean cityBuilding;
    private final Map<Integer, BuildingLevel> levels;

    public Building(String name, boolean cityBuilding, Map<Integer, BuildingLevel> levels) {
        this.name = name;
        this.cityBuilding = cityBuilding;
        this.levels = levels;
    }

    public String getName() {
        return name;
    }

    public boolean isCityBuilding() {
        return cityBuilding;
    }

    public Map<Integer, BuildingLevel> getLevels() {
        return levels;
    }
}
