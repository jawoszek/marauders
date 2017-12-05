package com.kawiory.marauders.game;

import com.kawiory.marauders.game.army.Unit;
import com.kawiory.marauders.game.city.Building;
import com.kawiory.marauders.game.resource.Resource;

import java.util.Map;

/**
 * @author Kacper
 */

public class Constants {

    private final Map<String, Resource> resources;
    private final Map<String, Building> buildings;
    private final Map<String, Unit> units;

    public Constants(Map<String, Resource> resources, Map<String, Building> buildings, Map<String, Unit> units) {
        this.resources = resources;
        this.buildings = buildings;
        this.units = units;
    }

    public Map<String, Resource> getResources() {
        return resources;
    }

    public Map<String, Building> getBuildings() {
        return buildings;
    }

    public Map<String, Unit> getUnits() {
        return units;
    }
}
