package com.kawiory.marauders.game.engine;

import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.game.PlayerData;
import com.kawiory.marauders.game.location.Location;

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
        game.getLocationsOnMap()
                .values()
                .forEach(
                        location -> resourceGatheringForCity(location, game)
                );
    }

    private void resourceGatheringForCity(Location location, Game game) {
        if (location.getOwnerName() == null) {
            return;
        }

        location.getBuildings()
                .forEach(
                        (building, level) ->
                                resourceGatheringForBuilding(
                                        building,
                                        level,
                                        game.getPlayersData()
                                                .get(
                                                        location.getOwnerName()))
                );
    }

    private void resourceGatheringForBuilding(String buildingName, Integer level, PlayerData playerData) {
        if (level < 1) {
            return;
        }
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
