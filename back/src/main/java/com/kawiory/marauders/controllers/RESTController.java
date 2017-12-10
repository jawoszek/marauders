package com.kawiory.marauders.controllers;

import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Constants;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
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
}
