package com.kawiory.marauders.player;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @author Kacper
 */

@Service
public class Players {
    private final Set<Player> playersSet;

    public Players(Set<Player> playersSet) {
        this.playersSet = playersSet;
    }

    public boolean containsPlayer(String name) {
        return playersSet
                .stream()
                .anyMatch(
                        player -> player.getName().equals(name)
                );
    }

    public Optional<Player> getPlayer(String name) {
        return playersSet
                .stream()
                .filter(player -> player.getName().equals(name))
                .findFirst();
    }

    public Set<Player> getPlayersSet() {
        return playersSet;
    }
}
