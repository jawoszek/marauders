package com.kawiory.marauders.game.army;

import com.kawiory.marauders.game.Copiable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class Army implements Copiable<Army> {

    private final Map<String, Integer> units;

    public Army() {
        this.units = new HashMap<>();
    }

    public Army(Map<String, Integer> units) {
        this.units = units;
    }

    public Map<String, Integer> getUnits() {
        return units;
    }

    @Override
    public Army copy() {
        return new Army(new HashMap<>(units));
    }
}
