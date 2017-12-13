package com.kawiory.marauders.game.army;

import com.kawiory.marauders.game.Copiable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class AlliedArmies implements Copiable<AlliedArmies> {
    private final Map<String, Army> armies;

    public AlliedArmies() {
        this.armies = new HashMap<>();
        armies.put("Rudy", new Army());
        armies.put("Skarbnik", new Army());
    }

    public AlliedArmies(Map<String, Army> armies) {
        this.armies = armies;
    }

    public Map<String, Army> getArmies() {
        return armies;
    }

    @Override
    public AlliedArmies copy() {
        return new AlliedArmies(Copiable.copy(armies));
    }
}
