package com.kawiory.marauders.game.commands;

import com.kawiory.marauders.MessagesSender;
import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.engine.GameCommandsQueue;
import com.kawiory.marauders.game.engine.Operations;
import io.vavr.Tuple;

import static java.lang.Integer.parseInt;

/**
 * @author Kacper
 */
public interface Command {

    String FAKAP = "FAKAP";

    Runnable convert(String userName, Operations operations, GameCommandsQueue gameCommandsQueue,
                     MessagesSender messagesSender);

    default Tuple parseIntegerPair(String tuple) {
        String[] split = tuple.replace("(", "").replace(")", "").split(", ");
        return Tuple.of(parseInt(split[0]), parseInt(split[1]));
    }
}
