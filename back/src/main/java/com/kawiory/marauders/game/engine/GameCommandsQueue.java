package com.kawiory.marauders.game.engine;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.Blob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

/**
 * @author Kacper
 */

@Service
public class GameCommandsQueue {

    private final Executor executor;
    private final Timer eventTimer;
    private final MessagesSender messagesSender;
    private final Blob blob;

    public GameCommandsQueue(@Qualifier("commandQueueExecutor") Executor executor,
                             Timer eventTimer, MessagesSender messagesSender, Blob blob) {
        this.executor = executor;
        this.eventTimer = eventTimer;
        this.messagesSender = messagesSender;
        this.blob = blob;
    }

    @Scheduled(fixedRate = 1000, initialDelay = 300)
    public void notifyAllUsers() {
        executor.execute(messagesSender::sendToAll);
    }

    @Scheduled(fixedRate = 1000)
    public void resourcesGathering() {
        executor.execute(new ResourcesGathering(blob));
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public void executeWithDelay(Runnable task, long delay) {
        eventTimer.schedule(getTimerTask(() -> execute(task)), delay);
    }

    private TimerTask getTimerTask(Runnable task) {
        return new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        };
    }
}
