package com.kawiory.marauders.controllers;

import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.game.PlayerData;
import com.kawiory.marauders.game.army.Army;
import com.kawiory.marauders.game.location.City;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import io.vavr.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Kacper
 */

@RestController
public class RESTController {

    @Autowired
    private Constants constants;

    @Autowired
    private Blob blob;

    @Autowired
    private GameCommandsQueue gameCommandsQueue;

    @RequestMapping(value = "/restBlob", method = RequestMethod.GET)
    public Blob blob(){
        return blob;
    }

    @RequestMapping(value = "/restGames", method = RequestMethod.GET)
    public Map<String, Game> games(){
        return blob.getGames();
    }

    @RequestMapping(value = "/restConstants", method = RequestMethod.GET)
    public Constants constants(){
        return constants;
    }

    public static Game getExample(){
        String skarbnik = "Skarbnik";
        String rudy = "Rudy";

        Army army1 = new Army();
        Army army2 = new Army();
        Army army3 = new Army();

        army1.getUnits().put("Levy", 1000);
        army2.getUnits().put("Levy", 10);
        army3.getUnits().put("Levy", 500);

        City city1 = new City();
        City city2 = new City();

        city1.getBuildings().put("City Hall", 2);
        city1.getGarrison().getArmies().put(skarbnik, army1);
        city1.getGarrison().getArmies().put(rudy, army2);
        city1.setOwnerName(skarbnik);

        city2.getBuildings().put("City Hall", 3);
        city2.getBuildings().put("Farm", 8);
        city2.getGarrison().getArmies().put(rudy, army3);
        city2.setOwnerName(rudy);

        PlayerData playerData1 = new PlayerData();
        PlayerData playerData2 = new PlayerData();
        playerData1.getResourcesOwned().put("Wood", 1000);
        playerData2.getResourcesOwned().put("Gold", 2);
        playerData2.getResourcesOwned().put("Food", 150);

        Game game = new Game();

        game.getCitiesOnMap().put(Tuple.of(2,5), city1);
        game.getCitiesOnMap().put(Tuple.of(4,3), city2);

        game.getPlayersData().put(skarbnik, playerData1);
        game.getPlayersData().put(rudy, playerData2);
        return game;
    }
}
