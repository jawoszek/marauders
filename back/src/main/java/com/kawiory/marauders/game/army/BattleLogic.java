package com.kawiory.marauders.game.army;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Kacper
 */

@Service
public class BattleLogic {

    public Map<String, AlliedArmies> battle(Map<String, AlliedArmies> initialAlliedArmies) {
        return initialAlliedArmies;
    }
}
