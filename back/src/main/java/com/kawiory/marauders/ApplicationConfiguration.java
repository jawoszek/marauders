package com.kawiory.marauders;

import com.kawiory.marauders.controllers.RESTController;
import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.initalization.InitializationService;
import com.kawiory.marauders.player.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Kacper
 */

@Configuration
@EnableScheduling
public class ApplicationConfiguration {

    private final InitializationService initializationService;

    public ApplicationConfiguration(InitializationService initializationService) {
        this.initializationService = initializationService;
    }

    @Bean
    public Blob initialBlob(Constants constants) {
        return new Blob(
                initialGames(),
                constants
        );
    }

    @Bean
    public Map<String, Game> initialGames() {
        Map<String, Game> games = new HashMap<>();
        games.put("1", RESTController.getExample());
        return games;
    }

    @Bean
    public Executor commandQueueExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public Timer commandQueueTimer() {
        return new Timer();
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
