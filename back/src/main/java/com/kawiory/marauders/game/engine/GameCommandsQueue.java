package com.kawiory.marauders.game.engine;

import com.kawiory.marauders.MessagesSender;
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
    private final MessagesSender messagesSender;

    public GameCommandsQueue(ResourcesGathering resourcesGathering,
                             @Qualifier("commandQueueExecutor") Executor executor,
                             Timer eventTimer, MessagesSender messagesSender) {
        this.resourcesGathering = resourcesGathering;
        this.executor = executor;
        this.eventTimer = eventTimer;
        this.messagesSender = messagesSender;
    }

    @Scheduled(fixedRate = 1000, initialDelay = 300)
    public void notifyAllUsers(){
        executor.execute(messagesSender::sendToAll);
    }

    @Scheduled(fixedRate = 1000)
    public void resourcesGathering(){
        executor.execute(resourcesGathering);
    }
}
