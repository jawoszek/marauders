package com.kawiory.marauders.game.city;

import java.util.Map;

/**
 * @author Kacper
 */
public class BuildingLevel {

    private final Requirements requirements;
    private final Map<String, Integer> resourcesPerSecond;
    private final Map<String, Integer> costs;

    public BuildingLevel(Requirements requirements, Map<String, Integer> resourcesPerSecond, Map<String, Integer> costs) {
        this.requirements = requirements;
        this.resourcesPerSecond = resourcesPerSecond;
        this.costs = costs;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public Map<String, Integer> getResourcesPerSecond() {
        return resourcesPerSecond;
    }

    public Map<String, Integer> getCosts() {
        return costs;
    }
}
