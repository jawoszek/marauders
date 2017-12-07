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
public class MoveTask implements Runnable {

    private static final String PLACE_DOES_NOT_EXIST = "ERROR - TARGET PLACE DOES NOT EXIST!";
    private static final String CANNOT_MOVE = "You do not have so many units!";
    private static final long MOVE_TIME = 10 * 1000;


    private final String userName;
    private final Tuple sourceCoordinates;
    private final Tuple targetCoordinates;
    private final Map<String, Integer> armyToSend;
    private final String gameName;

    private final Operations operations;
    private final GameCommandsQueue gameCommandsQueue;
    private final MessagesSender messagesSender;

    public MoveTask(String userName, Tuple sourceCoordinates, Tuple targetCoordinates, Map<String, Integer> armyToSend,
                    String gameName, Operations operations, GameCommandsQueue gameCommandsQueue,
                    MessagesSender messagesSender) {
        this.userName = userName;
        this.sourceCoordinates = sourceCoordinates;
        this.targetCoordinates = targetCoordinates;
        this.armyToSend = armyToSend;
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
        if (!operations.placeExists(gameName, targetCoordinates)) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, PLACE_DOES_NOT_EXIST));
            return;
        }

        if (!operations.hasUnits(armyToSend, userName, gameName, sourceCoordinates)) {
            gameCommandsQueue.execute(() -> messagesSender.sendToUser(userName, CANNOT_MOVE));
            return;
        }

        operations.removeUnits(armyToSend, userName, gameName, sourceCoordinates);
        gameCommandsQueue.executeWithDelay(
                () -> operations.addArmy(armyToSend, userName, gameName, targetCoordinates, sourceCoordinates),
                MOVE_TIME
        );
    }


}
