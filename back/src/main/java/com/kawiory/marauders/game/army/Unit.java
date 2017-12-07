package com.kawiory.marauders.game.army;

import com.kawiory.marauders.game.location.Requirements;

import java.util.Map;

/**
 * @author Kacper
 */
public class Unit {

    private final String name;
    private final int attack;
    private final int defense;
    private final int speed;
    private final int position;
    private final int range;

    private final Map<String, Integer> resourcesPerSecond;
    private final Map<String, Integer> costs;
    private final Requirements requirements;

    public Unit(String name, int attack, int defense, int speed, int position, int range, Map<String, Integer> resourcesPerSecond, Map<String, Integer> costs, Requirements requirements) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.position = position;
        this.range = range;
        this.resourcesPerSecond = resourcesPerSecond;
        this.costs = costs;
        this.requirements = requirements;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPosition() {
        return position;
    }

    public int getRange() {
        return range;
    }

    public Map<String, Integer> getResourcesPerSecond() {
        return resourcesPerSecond;
    }

    public Map<String, Integer> getCosts() {
        return costs;
    }

    public Requirements getRequirements() {
        return requirements;
    }
}
