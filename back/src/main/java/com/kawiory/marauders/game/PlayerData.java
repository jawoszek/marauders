package com.kawiory.marauders.game;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class PlayerData {
    private final Map<String, Integer> resourcesOwned;
    private final boolean isAI;
    private final String alliance;

    public PlayerData(boolean isAI, String alliance) {
        this.isAI = isAI;
        this.alliance = alliance;
        this.resourcesOwned = new HashMap<>();
    }

    public Map<String, Integer> getResourcesOwned() {
        return resourcesOwned;
    }

    public boolean isAI() {
        return isAI;
    }

    public String getAlliance() {
        return alliance;
    }
}
