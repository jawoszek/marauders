package com.kawiory.marauders.game.resource;

/**
 * @author Kacper
 */
public enum Resource {
    FOOD ("Food"),
    WOOD ("Wood"),
    STONE ("Stone"),
    GOLD ("Gold");

    private final String name;

    Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
