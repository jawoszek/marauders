package com.kawiory.marauders.game.engine;

import com.kawiory.marauders.game.Blob;
import com.kawiory.marauders.game.Copiable;
import com.kawiory.marauders.game.Game;
import com.kawiory.marauders.game.army.AlliedArmies;
import com.kawiory.marauders.game.army.BattleLogic;
import com.kawiory.marauders.game.army.BattleResult;
import com.kawiory.marauders.game.location.Location;
import io.vavr.Tuple;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kacper
 */

@Service
public class Operations {

    private final Blob blob;
    private final BattleLogic battleLogic;

    public Operations(Blob blob, BattleLogic battleLogic) {
        this.blob = blob;
        this.battleLogic = battleLogic;
    }

    public boolean isOwner(String userName, String gameName, Tuple coordinates) {
        return blob
                .getGames()
                .get(gameName)
                .getLocationsOnMap()
                .get(coordinates)
                .getOwnerName()
                .equals(userName);
    }

    public boolean containsBuilding(String buildingName, int buildingLevel, String gameName, Tuple coordinates) {
        return blob.getGames()
                .get(gameName)
                .getLocationsOnMap()
                .get(coordinates)
                .getBuildings()
                .getOrDefault(buildingName, 0) == buildingLevel;
    }

    public Map<String, Integer> getBuildingCosts(String buildingName, int buildingLevel) {
        return blob.getConstants()
                .getBuildings()
                .get(buildingName)
                .getLevels()
                .get(buildingLevel)
                .getCosts();
    }

    public boolean buildingCanBeBuild(String buildingName, int buildingLevel, String userName, String gameName, Tuple coordinates) {
        Map<String, Integer> costs = getBuildingCosts(buildingName, buildingLevel);

        return hasResources(costs, userName, gameName) &&
                buildingMeetRequirements(buildingName, buildingLevel, userName, gameName, coordinates);

    }

    public boolean buildingMeetRequirements(String buildingName, int buildingLevel, String userName, String gameName, Tuple coordinates) {
        return blob.getConstants()
                .getBuildings()
                .get(buildingName)
                .getLevels()
                .get(buildingLevel)
                .getRequirements()
                .getBuildings()
                .entrySet()
                .stream()
                .allMatch(
                        requiredBuilding ->
                                containsBuilding(
                                        requiredBuilding.getKey(),
                                        requiredBuilding.getValue(),
                                        gameName,
                                        coordinates
                                )
                ) &&
                containsBuilding(buildingName, buildingLevel - 1, gameName, coordinates);
    }

    public boolean hasResources(Map<String, Integer> resources, String userName, String gameName) {
        return resources
                .entrySet()
                .stream()
                .allMatch(
                        resourcesToCheck -> blob.getGames()
                                .get(gameName)
                                .getPlayersData()
                                .get(userName)
                                .getResourcesOwned()
                                .getOrDefault(resourcesToCheck.getKey(), 0) >= resourcesToCheck.getValue()
                );
    }

    public void useResources(Map<String, Integer> resources, String userName, String gameName) {
        resources.forEach(
                (resource, amount) ->
                        blob.getGames()
                                .get(gameName)
                                .getPlayersData()
                                .get(userName)
                                .getResourcesOwned()
                                .merge(resource, amount, (current, toRemove) -> current - toRemove)
        );
    }

    public boolean hasUnits(Map<String, Integer> units, String userName, String gameName, Tuple coordinates) {
        return units
                .entrySet()
                .stream()
                .allMatch(
                        unitsToCheck ->
                                blob.getGames()
                                        .get(gameName)
                                        .getLocationsOnMap()
                                        .get(coordinates)
                                        .getAlliedArmies()
                                        .get(getPlayerAlliance(gameName, userName))
                                        .getArmies()
                                        .get(userName)
                                        .getUnits()
                                        .getOrDefault(unitsToCheck.getKey(), 0) >= unitsToCheck.getValue()
                );
    }

    public void removeUnits(Map<String, Integer> units, String userName, String gameName, Tuple coordinates) {
        units.forEach(
                (unit, amount) ->
                        blob.getGames()
                                .get(gameName)
                                .getLocationsOnMap()
                                .get(coordinates)
                                .getAlliedArmies()
                                .get(getPlayerAlliance(gameName, userName))
                                .getArmies()
                                .get(userName)
                                .getUnits()
                                .merge(unit, amount, (current, toRemove) -> current - toRemove)
        );
    }

    public boolean placeExists(String gameName, Tuple coordinates) {
        return blob.getGames()
                .get(gameName)
                .getLocationsOnMap()
                .containsKey(coordinates);
    }

    public void build(String buildingName, int buildingLevel, String gameName, Tuple coordinates) {
        blob.getGames()
                .get(gameName)
                .getLocationsOnMap()
                .get(coordinates)
                .getBuildings()
                .put(buildingName, buildingLevel);
    }

    public void addArmy(Map<String, Integer> units, String userName, String gameName, Tuple coordinates,
                        Tuple sourceCoordinates) {
        Game game = blob
                .getGames()
                .get(gameName);

        units.forEach(
                (unit, amount) ->
                        blob
                                .getGames()
                                .get(gameName)
                                .getLocationsOnMap()
                                .get(coordinates)
                                .getAlliedArmies()
                                .get(getPlayerAlliance(gameName, userName))
                                .getArmies()
                                .get(userName)
                                .getUnits()
                                .merge(unit, amount, (current, toAdd) -> current + toAdd)
        );

        if (enemiesMetInLocation(gameName, coordinates)) {
            battle(gameName, coordinates);
        }
    }

    private boolean enemiesMetInLocation(String gameName, Tuple coordinates) {
        Game game = blob
                .getGames()
                .get(gameName);

        return game
                .getLocationsOnMap()
                .get(coordinates)
                .getAlliedArmies()
                .size() > 1;
    }

    private void battle(String gameName, Tuple coordinates) {
        Location location = blob
                .getGames()
                .get(gameName)
                .getLocationsOnMap()
                .get(coordinates);

        Map<String, AlliedArmies> initialAlliedArmies = Copiable.copy(location.getAlliedArmies());
        Map<String, AlliedArmies> finalAlliedArmies = Copiable.copy(initialAlliedArmies);

        location.getBattleResults().add(new BattleResult(LocalDateTime.now(), initialAlliedArmies, finalAlliedArmies));
    }

    public boolean canRecruit(Map<String, Integer> units, String userName, String gameName, Tuple coordinates) {
        Map<String, Integer> costs = getUnitsCosts(units);

        return hasResources(costs, userName, gameName) &&
                units.keySet()
                        .stream()
                        .allMatch(unit -> unitMeetRequirement(unit, gameName, coordinates));
    }

    public Map<String, Integer> getUnitsCosts(Map<String, Integer> units) {
        return units
                .entrySet()
                .stream()
                .flatMap(
                        unitAndAmount ->
                                blob.getConstants()
                                        .getUnits()
                                        .get(unitAndAmount.getKey())
                                        .getCosts()
                                        .entrySet()
                                        .stream()
                )
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue()
                                        .stream()
                                        .mapToInt(Map.Entry::getValue)
                                        .sum()
                        )
                );
    }

    public boolean unitMeetRequirement(String unitName, String gameName, Tuple coordinates) {
        return blob
                .getConstants()
                .getUnits()
                .get(unitName)
                .getRequirements()
                .getBuildings()
                .entrySet()
                .stream()
                .allMatch(
                        buildingAndLevel ->
                                containsBuilding(
                                        buildingAndLevel.getKey(),
                                        buildingAndLevel.getValue(),
                                        gameName,
                                        coordinates)
                );
    }

    private String getPlayerAlliance(String gameName, String playerName) {
        return blob
                .getGames()
                .get(gameName)
                .getPlayersData()
                .get(playerName)
                .getAlliance();
    }


}
