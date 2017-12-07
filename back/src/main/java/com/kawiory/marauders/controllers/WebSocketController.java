package com.kawiory.marauders.controllers;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.commands.Build;
import com.kawiory.marauders.game.commands.Move;
import com.kawiory.marauders.game.commands.Recruit;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
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

    private final GameCommandsQueue gameCommandsQueue;
    private final Operations operations;
    private final MessagesSender messagesSender;

    public WebSocketController(GameCommandsQueue gameCommandsQueue, Operations operations, MessagesSender messagesSender) {
        this.gameCommandsQueue = gameCommandsQueue;
        this.operations = operations;
        this.messagesSender = messagesSender;
    }

    @MessageMapping("/commands")
    public void commands(Message<Object> message, @Payload Object command) throws Exception {
        System.out.println("commands");
    }

    @MessageMapping("/commands/build")
    public void build(Message<Object> message, @Payload Build command) throws Exception {
        gameCommandsQueue.execute(command.convert(getUserName(message), operations, gameCommandsQueue, messagesSender));
    }

    @MessageMapping("/commands/move")
    public void move(Message<Object> message, @Payload Move command) throws Exception {
        gameCommandsQueue.execute(command.convert(getUserName(message), operations, gameCommandsQueue, messagesSender));
    }

    @MessageMapping("/commands/recruit")
    public void recruit(Message<Object> message, @Payload Recruit command) throws Exception {
        gameCommandsQueue.execute(command.convert(getUserName(message), operations, gameCommandsQueue, messagesSender));
    }

    private String getUserName(Message<Object> message) {
        return message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class).getName();
    }


}
