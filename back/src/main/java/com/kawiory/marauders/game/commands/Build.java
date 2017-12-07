package com.kawiory.marauders.game.commands;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
import com.kawiory.marauders.game.tasks.BuildTask;

/**
 * @author Kacper
 */
public class Build implements Command {

    private String buildingName;
    private int buildingLevel;
    private String cityCoordinates;
    private String gameName;

    public Build() {
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void setBuildingLevel(int buildingLevel) {
        this.buildingLevel = buildingLevel;
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
        return new BuildTask(userName, buildingName, buildingLevel, parseIntegerPair(cityCoordinates), gameName,
                operations, gameCommandsQueue, messagesSender);
    }
}
