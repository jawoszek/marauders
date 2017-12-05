package com.kawiory.marauders;

import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.initalization.InitializationService;
import com.kawiory.marauders.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.Executors;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Kacper
 */

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private InitializationService initializationService;

    @Bean
    public GameCommandsQueue gameCommandsQueue() {
        return new GameCommandsQueue(
                Executors.newSingleThreadExecutor()
        );
    }

    @Bean
    public Set<Player> initialPlayers() {
        return newHashSet(
                new Player("Rudy"),
                new Player("Null")
        );
    }

    @Bean
    public Constants constants() {
        return initializationService.initializeContants();
    }
}
