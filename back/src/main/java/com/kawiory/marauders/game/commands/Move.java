package com.kawiory.marauders.game.commands;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
import com.kawiory.marauders.game.tasks.MoveTask;

import java.util.Map;

/**
 * @author Kacper
 */
public class Move implements Command {

    private String sourceCoordinates;
    private String targetCoordinates;
    private Map<String, Integer> armyToSend;
    private String gameName;

    public Move() {
    }

    public String getSourceCoordinates() {
        return sourceCoordinates;
    }

    public void setSourceCoordinates(String sourceCoordinates) {
        this.sourceCoordinates = sourceCoordinates;
    }

    public String getTargetCoordinates() {
        return targetCoordinates;
    }

    public void setTargetCoordinates(String targetCoordinates) {
        this.targetCoordinates = targetCoordinates;
    }

    public Map<String, Integer> getArmyToSend() {
        return armyToSend;
    }

    public void setArmyToSend(Map<String, Integer> armyToSend) {
        this.armyToSend = armyToSend;
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
        return new MoveTask(
                userName,
                parseIntegerPair(sourceCoordinates),
                parseIntegerPair(targetCoordinates),
                armyToSend,
                gameName,
                operations,
                gameCommandsQueue,
                messagesSender);
    }
}
