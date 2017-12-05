package com.kawiory.marauders.game.city;

import com.kawiory.marauders.game.army.Garrison;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class City {

    private String ownerName;
    private final Map<String, Integer> buildings;
    private final Garrison garrison;

    public City() {
        buildings = new HashMap<>();
        garrison = new Garrison();
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
}
