package com.kawiory.marauders.game;

import com.kawiory.marauders.game.location.Location;
import io.vavr.Tuple;

import java.util.List;
import java.util.Map;

/**
 * @author Kacper
 */
public class Game {

    private final Map<Tuple, Location> locationsOnMap;
    private final Map<String, PlayerData> playersData;
    private final List<String> alliances;

    public Game(Map<Tuple, Location> locationsOnMap, Map<String, PlayerData> playersData, List<String> alliances) {
        this.locationsOnMap = locationsOnMap;
        this.playersData = playersData;
        this.alliances = alliances;
    }

    public Map<Tuple, Location> getLocationsOnMap() {
        return locationsOnMap;
    }

    public Map<String, PlayerData> getPlayersData() {
        return playersData;
    }

    public List<String> getAlliances() {
        return alliances;
    }
}
