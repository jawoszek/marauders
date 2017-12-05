package com.kawiory.marauders.game.engine;

import java.util.concurrent.Executor;

/**
 * @author Kacper
 */

public class GameCommandsQueue {

    private final Executor executor;

    public GameCommandsQueue(Executor executor) {
        this.executor = executor;
    }


}
