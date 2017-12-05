package com.kawiory.marauders.game;

import java.util.Map;

/**
 * @author Kacper
 */
public class Blob {
    private final Map<String, Game> games;
    private final Constants constants;

    public Blob(Map<String, Game> games, Constants constants) {
        this.games = games;
        this.constants = constants;
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public Constants getConstants() {
        return constants;
    }
}
