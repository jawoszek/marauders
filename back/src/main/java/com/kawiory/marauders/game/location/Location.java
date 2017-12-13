package com.kawiory.marauders.game.location;

import com.kawiory.marauders.game.army.BattleResult;
import com.kawiory.marauders.game.army.AlliedArmies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final Map<String, AlliedArmies> alliedArmies;
    private final List<BattleResult> battleResults;

    public Location(String type, Map<String, Integer> buildings) {
        this.type = type;
        this.buildings = buildings;
        this.alliedArmies = new HashMap<>();
        this.battleResults = new ArrayList<>();
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

    public Map<String, AlliedArmies> getAlliedArmies() {
        return alliedArmies;
    }

    public String getType() {
        return type;
    }

    public List<BattleResult> getBattleResults() {
        return battleResults;
    }
}
