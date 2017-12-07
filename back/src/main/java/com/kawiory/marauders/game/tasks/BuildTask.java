package com.kawiory.marauders.game.tasks;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
import io.vavr.Tuple;

import static com.kawiory.marauders.game.commands.Command.FAKAP;

/**
 * @author Kacper
 */
public class BuildTask implements Runnable {

    private static final String NOT_AN_OWNER = "ERROR - YOU ARE NOT AN OWNER!";
    private static final String CANNOT_BUILD = "You cannot build that building now!";

    private final String userName;
    private final String buildingName;
    private final int buildingLevel;
    private final Tuple cityCoordinates;
    private final String gameName;

    private final Operations operations;
    private final GameCommandsQueue gameCommandsQueue;
    private final MessagesSender messagesSender;

    public BuildTask(String userName, String buildingName, int buildingLevel, Tuple cityCoordinates, String gameName,
                     Operations operations, GameCommandsQueue gameCommandsQueue, MessagesSender messagesSender) {
        this.userName = userName;
        this.buildingName = buildingName;
        this.buildingLevel = buildingLevel;
        this.cityCoordinates = cityCoordinates;
        this.gameName = gameName;
        this.operations = operations;
        this.gameCommandsQueue = gameCommandsQueue;
        this.messagesSender = messagesSender;
    }

    @Override
    public void run() {
        try {
            runTask();
        } catch (Exception e) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, FAKAP));
        }
    }

    public void runTask() {
        if (!operations.isOwner(userName, gameName, cityCoordinates)) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, NOT_AN_OWNER));
            return;
        }

        if (!operations.buildingCanBeBuild(buildingName, buildingLevel, userName, gameName, cityCoordinates)) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, CANNOT_BUILD));
            return;
        }

        operations.useResources(operations.getBuildingCosts(buildingName, buildingLevel), userName, gameName);
        operations.build(buildingName, buildingLevel, gameName, cityCoordinates);
    }
}
