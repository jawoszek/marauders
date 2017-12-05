package com.kawiory.marauders.controllers;

import com.kawiory.marauders.game.Game;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Kacper
 */

@Controller
public class WebSocketController {

    @MessageMapping("/healthCheck")
    public Game healthCheck() {
        return new Game();
    }
}
