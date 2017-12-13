package com.kawiory.marauders.game.army;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Kacper
 */

public class BattleResult {

    private final LocalDateTime time;
    private final Map<String, AlliedArmies> initialAlliedArmies;
    private final Map<String, AlliedArmies> finalAlliedArmies;

    public BattleResult(LocalDateTime time, Map<String, AlliedArmies> initialAlliedArmies,
                        Map<String, AlliedArmies> finalAlliedArmies) {
        this.time = time;
        this.initialAlliedArmies = initialAlliedArmies;
        this.finalAlliedArmies = finalAlliedArmies;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Map<String, AlliedArmies> getInitialAlliedArmies() {
        return initialAlliedArmies;
    }

    public Map<String, AlliedArmies> getFinalAlliedArmies() {
        return finalAlliedArmies;
    }
}
