package com.kawiory.marauders.game;

import com.kawiory.marauders.game.city.City;
import io.vavr.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class Game {

    private final Map<Tuple, City> citiesOnMap;
    private final Map<String, PlayerData> playersData;

    public Game() {
        citiesOnMap = new HashMap<>();
        playersData = new HashMap<>();
    }

    public Map<Tuple, City> getCitiesOnMap() {
        return citiesOnMap;
    }

    public Map<String, PlayerData> getPlayersData() {
        return playersData;
    }
}
