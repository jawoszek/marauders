package com.kawiory.marauders;

import com.kawiory.marauders.game.Blob;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Kacper
 */

@Service
public class MessagesSender {

    private static final String CHANNEL = "/blob";

    private final Blob blob;
    private final SimpMessagingTemplate template;

    public MessagesSender(Blob blob, SimpMessagingTemplate template) {
        this.blob = blob;
        this.template = template;
    }

    public void sendToAll() {
        blob.getGames()
                .values()
                .stream()
                .flatMap(
                        game -> game
                                .getPlayersData()
                                .keySet()
                                .stream())
                .distinct()
                .forEach(user -> sendBlob(user, CHANNEL, blob));

    }

    private void sendBlob(String userName, String channel, Blob blob) {
        template.convertAndSendToUser(userName, channel, blob);
    }
}