package com.kawiory.marauders.game;

import com.kawiory.marauders.game.location.Location;
import io.vavr.Tuple;

import java.util.Map;

/**
 * @author Kacper
 */
public class Game {

    private final Map<Tuple, Location> locationsOnMap;
    private final Map<String, PlayerData> playersData;

    public Game(Map<Tuple, Location> locationsOnMap, Map<String, PlayerData> playersData) {
        this.locationsOnMap = locationsOnMap;
        this.playersData = playersData;
    }

    public Map<Tuple, Location> getLocationsOnMap() {
        return locationsOnMap;
    }

    public Map<String, PlayerData> getPlayersData() {
        return playersData;
    }
}
