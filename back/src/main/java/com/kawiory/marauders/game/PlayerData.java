package com.kawiory.marauders.game;

import com.kawiory.marauders.game.resource.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kacper
 */
public class PlayerData {
    private final Map<String, Integer> resourcesOwned;

    public PlayerData() {
        this.resourcesOwned = new HashMap<>();
    }

    public Map<String, Integer> getResourcesOwned() {
        return resourcesOwned;
    }
}
