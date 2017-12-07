package com.kawiory.marauders.game.engine;

import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.game.PlayerData;
import com.kawiory.marauders.game.location.City;

/**
 * @author Kacper
 */

public class ResourcesGathering implements Runnable {

    private final Blob blob;

    public ResourcesGathering(Blob blob) {
        this.blob = blob;
    }

    @Override
    public void run() {
        blob.getGames()
                .values()
                .forEach(this::resourceGatheringForGame);
    }

    private void resourceGatheringForGame(Game game) {
        game.getCitiesOnMap()
                .values()
                .forEach(
                        city -> resourceGatheringForCity(city, game)
                );
    }

    private void resourceGatheringForCity(City city, Game game) {
        if (city.getOwnerName() == null) {
            return;
        }

        city.getBuildings()
                .forEach(
                        (building, level) ->
                                resourceGatheringForBuilding(
                                        building,
                                        level,
                                        game.getPlayersData()
                                                .get(
                                                        city.getOwnerName()))
                );
    }

    private void resourceGatheringForBuilding(String buildingName, Integer level, PlayerData playerData) {
        blob.getConstants()
                .getBuildings()
                .get(buildingName)
                .getLevels()
                .get(level)
                .getResourcesPerSecond()
                .forEach(
                        (resource, value) -> gatherResource(resource, value, playerData)
                );
    }

    private void gatherResource(String resource, Integer value, PlayerData playerData) {
        playerData.getResourcesOwned().merge(resource, value, Integer::sum);
    }
}
