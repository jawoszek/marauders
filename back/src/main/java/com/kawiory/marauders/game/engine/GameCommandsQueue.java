package com.kawiory.marauders.game.engine;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.concurrent.Executor;

/**
 * @author Kacper
 */

@Service
public class GameCommandsQueue {

    private final ResourcesGathering resourcesGathering;
    private final Executor executor;
    private final Timer eventTimer;

    public GameCommandsQueue(ResourcesGathering resourcesGathering,
                             @Qualifier("commandQueueExecutor") Executor executor,
                             Timer eventTimer) {
        this.resourcesGathering = resourcesGathering;
        this.executor = executor;
        this.eventTimer = eventTimer;
    }

    @Scheduled(fixedRate = 1000)
    public void resourcesGathering(){
        executor.execute(resourcesGathering);
    }
}
