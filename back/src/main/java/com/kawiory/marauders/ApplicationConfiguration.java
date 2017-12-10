package com.kawiory.marauders;

import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.game.PlayerData;
import com.kawiory.marauders.game.army.Army;
import com.kawiory.marauders.game.location.Location;
import com.kawiory.marauders.initalization.InitializationService;
import io.vavr.Tuple;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.kawiory.marauders.game.location.Location.CITY_TYPE;

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
                initialGames(constants),
                constants
        );
    }

    @Bean
    public Map<String, Game> initialGames(Constants constants) {
        Map<String, Game> games = new HashMap<>();
        Map<String, PlayerData> initialData = new HashMap<>();
        initialData.put("Rudy", new PlayerData());
        initialData.put("Skarbnik", new PlayerData());
        games.put("1", new Game(initializationService.initializeLocations(constants), initialData));
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
    public Constants constants() {
        return initializationService.initializeConstants();
    }
}
