function sendBuildCommand(coords,building) {
    console.log(building.name);
    console.log(building.level);
    console.log(coords);
    stompClient.send("/marauders/commands/build", {},
        JSON.stringify(
            {
                "buildingName": String(building.name),
                "buildingLevel": String(building.level),
                "cityCoordinates": coords,
                "gameName": "1"
            })
    );
}

function sendRecruitCommand(unit,coords){
    var armyToRecruit = {};
    armyToRecruit[unit] = 1;

    console.log(armyToRecruit);
    console.log(coords);
    stompClient.send("/marauders/commands/recruit", {},
        JSON.stringify(
            {
                "armyToRecruit": armyToRecruit,
                "cityCoordinates": coords,
                "gameName": "1"
            })
    );
}

function moveArmyCommand(armyInfo){
    console.log(armyInfo);
    stompClient.send("/marauders/commands/move", {},
        JSON.stringify(
            {
                "sourceCoordinates": armyInfo["sourceCoordinates"],
                "targetCoordinates": armyInfo["targetCoordinates"],
                "armyToSend": armyInfo["armyToSend"],
                "gameName": armyInfo["gameName"]
            })
    );
}