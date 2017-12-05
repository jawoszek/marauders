package com.kawiory.marauders.game.army;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class Garrison {
    private final Map<String, Army> armies;

    public Garrison() {
        this.armies = new HashMap<>();
    }

    public Map<String, Army> getArmies() {
        return armies;
    }
}
