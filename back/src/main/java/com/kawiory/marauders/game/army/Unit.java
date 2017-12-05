package com.kawiory.marauders.game.army;

/**
 * @author Kacper
 */
public enum Unit {
    LEVY ("Levy", 1, 1, 5, 4, 0);

    private final String name;
    private final int attack;
    private final int defense;
    private final int speed;
    private final int position;
    private final int range;

    Unit(String name, int attack, int defense, int speed, int position, int range) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.position = position;
        this.range = range;
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
}
