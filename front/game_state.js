function updateGameState(){
    var gameState = gameStateMock;
    if(!(gameState.games["1"]).locationsOnMap || !(gameState.games["1"]).playersData)
        return;
    return gameState;
}

function getCity(gameState,coords){
    var cities = (gameState.games["1"]).locationsOnMap;
    for(cityCoord in cities){
        if(coords == cityCoord)
            return cities[cityCoord]
    }
}
function getPlayerResources(gameState,playerID){
    if(!(gameState.games["1"]).playersData[playerID]) return;
    return ((gameState.games["1"]).playersData[playerID]).resourcesOwned;
}
var gameStateMock={
    "games":{
    },
    "constants":{
    }
};
function getUnits(gameState,cityCoords,playerID){
    var city = (gameState.games["1"]).locationsOnMap[cityCoords];
    if(!city) return;
    var playerArmy= city.garrison.armies[playerID];
    if(!playerArmy) return;
    return playerArmy.units;
}

function getAllUnits(gameState){
    return gameState.constants.units;
}