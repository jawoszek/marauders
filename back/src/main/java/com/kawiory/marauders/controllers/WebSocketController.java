package com.kawiory.marauders.controllers;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author Kacper
 */

@Controller
public class WebSocketController {

    @MessageMapping("/commands")
    public void commands(Message<Object> message, @Payload Object chatMessage) throws Exception {
        System.out.println("COMMANDS PING'D");
    }
}
