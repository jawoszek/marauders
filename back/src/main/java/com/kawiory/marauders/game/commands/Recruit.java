package com.kawiory.marauders.game.commands;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
import com.kawiory.marauders.game.tasks.RecruitTask;

import java.util.Map;

/**
 * @author Kacper
 */
public class Recruit implements Command {

    private Map<String, Integer> armyToRecruit;
    private String cityCoordinates;
    private String gameName;

    public Recruit() {
    }

    public Map<String, Integer> getArmyToRecruit() {
        return armyToRecruit;
    }

    public void setArmyToRecruit(Map<String, Integer> armyToRecruit) {
        this.armyToRecruit = armyToRecruit;
    }

    public String getCityCoordinates() {
        return cityCoordinates;
    }

    public void setCityCoordinates(String cityCoordinates) {
        this.cityCoordinates = cityCoordinates;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public Runnable convert(String userName, Operations operations, GameCommandsQueue gameCommandsQueue,
                            MessagesSender messagesSender) {
        return new RecruitTask(userName, armyToRecruit, parseIntegerPair(cityCoordinates), gameName,
                operations, gameCommandsQueue, messagesSender);
    }
}
