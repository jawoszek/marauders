package com.kawiory.marauders.game.tasks;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
import io.vavr.Tuple;

import java.util.Map;

import static com.kawiory.marauders.game.commands.Command.FAKAP;

/**
 * @author Kacper
 */
public class RecruitTask implements Runnable {

    private static final String NOT_AN_OWNER = "ERROR - YOU ARE NOT AN OWNER!";
    private static final String CANNOT_RECRUIT = "You cannot recruit such army now!";
    private static final long RECRUIT_TIME = 5 * 1000;


    private final String userName;
    private final Map<String, Integer> armyToRecruit;
    private final Tuple cityCoordinates;
    private final String gameName;

    private final Operations operations;
    private final GameCommandsQueue gameCommandsQueue;
    private final MessagesSender messagesSender;

    public RecruitTask(String userName, Map<String, Integer> armyToRecruit, Tuple cityCoordinates, String gameName,
                       Operations operations, GameCommandsQueue gameCommandsQueue, MessagesSender messagesSender) {
        this.userName = userName;
        this.armyToRecruit = armyToRecruit;
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
            e.printStackTrace();
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, FAKAP));
        }
    }

    private void runTask() {
        if (!operations.isOwner(userName, gameName, cityCoordinates)) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, NOT_AN_OWNER));
            return;
        }

        if (!operations.canRecruit(armyToRecruit, userName, gameName, cityCoordinates)) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, CANNOT_RECRUIT));
            return;
        }

        operations.useResources(operations.getUnitsCosts(armyToRecruit), userName, gameName);
        gameCommandsQueue.executeWithDelay(
                () -> operations.addArmy(armyToRecruit, userName, gameName, cityCoordinates, null),
                RECRUIT_TIME
        );
    }
}
