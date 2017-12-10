package com.kawiory.marauders.game.location;

import com.kawiory.marauders.game.army.Garrison;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class Location {

    public static final String CITY_TYPE = "City";
    public static final String GOLD_MINE_TYPE = "Gold Mine";

    private String ownerName;
    private final String type;
    private final Map<String, Integer> buildings;
    private final Garrison garrison;

    public Location(String type, Map<String, Integer> buildings) {
        this.type = type;
        this.buildings = buildings;
        this.garrison = new Garrison();
    }

    public Map<String, Integer> getBuildings() {
        return buildings;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Garrison getGarrison() {
        return garrison;
    }

    public String getType() {
        return type;
    }
}
