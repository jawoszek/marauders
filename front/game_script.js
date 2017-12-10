var a = $("#game");
var renderedObjects = {};
var refreshTargets = [];
var clouds = [];
var background;
var playerID = "Rudy";
var currentCityCoords;
var cloud1;
var cloud2;
var cloud3;
var cityAscii;
var buildingInProcess;
var currentGameState;
var stompClient;
var buildingSites = {
    "Farm": {x: 39, y: 3},
    "City Hall": {x: 30, y: 6},
    "Woodcutters Camp": {x: 65, y: 4},
    "Fishing Port": {x: 62, y: 10},
    "Quarry": {x: 0, y: 7}
};

function connect() {
    console.log("wut1");
    stompClient.connect('', '', function (frame) {
        console.log("wut2");
        stompClient.subscribe('/user/queue/blob', function (message) {
            refresh(frame, message)
        });
        stompClient.subscribe('/user/queue/messages', function (message) {
            alert(message.body)
        });
    });
}

function refresh(frame, message) {
    playerID = frame.headers['user-name'];
    currentGameState = JSON.parse(message.body);
    refreshTargets.forEach(Function.prototype.call, Function.prototype.call);
}

function renderWorldMap() {
    currentCityCoords = null;
    resetViews();
    refreshTargets.push(renderResources);

    currentGameState = gameStateMock;
    console.log(currentGameState);
    cloud1 = new AsciiObj(cloudBig, 1, 2, 10, new AsciiObjProps("cloud1", "hiCloud"));
    cloud2 = new AsciiObj(cloudMed, 25, 2, 10, new AsciiObjProps("cloud2", "hiCloud"));
    cloud3 = new AsciiObj(cloudSmall1, 45, 1, 10, new AsciiObjProps("cloud3", "hiCloud"));
    cityAscii = new AsciiObj(city1, 0, 0, 0, null);
    var worldObj = new AsciiObj(world, 0, 0, 1, null);
    var cityObjOnMap1 = new AsciiObj(cityOnMap, 95, 18, 2, new AsciiObjProps("(2, 5)", "goToCity"));
    var cityObjOnMap2 = new AsciiObj(cityOnMap2, 11, 15, 2, new AsciiObjProps("(4, 3)", "goToCity"));
    background = backgroundHuge;
    clouds = [cloud1, cloud2, cloud3];
    renderedObjects["world"] = worldObj;
    renderedObjects["cityOnMap1"] = cityObjOnMap1;
    renderedObjects["cityOnMap2"] = cityObjOnMap2;
}

function hiCloud(id) {
    console.log("it's me a " + id);
}

function goToCity(coords) {
    resetViews();
    refreshTargets.push(renderBuildings);
    refreshTargets.push(renderResources);
    refreshTargets.push(renderGarrison);

    currentCityCoords = coords;
    background = backgroundBig;
    console.log(currentGameState);
    renderedObjects["city"] = new AsciiObj(city1, 0, 0, 0, null);
    renderBuildings();
    renderedObjects["cloud1"] = cloud1;
    renderedObjects["cloud2"] = cloud2;
    renderedObjects["cloud3"] = cloud3;
    var backButtonProps = new AsciiObjProps("backButton", "renderWorldMap");
    renderedObjects["backButton"] = new AsciiObj(backButton, 0, 18, 15, backButtonProps);

    var militaryPanelButtonProps = new AsciiObjProps("militaryPanelButton", "renderMilitaryPanel");
    renderedObjects["militaryPanelButton"] = new AsciiObj(militaryPanelButton, 70, 20, 15, militaryPanelButtonProps);
    renderGarrison();
}

function renderResources() {
    var resources = getPlayerResources(currentGameState, playerID);
    var resourceText = generateResourceForm(resources.Food, resources.Wood, resources.Stone, resources.Gold);
    if (currentCityCoords)
        renderedObjects["resources"] = new AsciiObj(resourceText, backButton[0].length, city1.length, 10, null);
    else
        renderedObjects["resources"] = new AsciiObj(resourceText, backButton[0].length, world.length, 10, null);
}

function renderGarrison() {
    var militaryForm = generateGarrisonForm();

    var units = getUnits(currentGameState, currentCityCoords, playerID);
    for (unit in units) {
        var numOfUnit = units[unit];
        var strNum = String(numOfUnit);
        militaryForm.splice(3, 0, militaryForm[2]);
        militaryForm[3] = militaryForm[2].slice(0, 2)
            + unit
            + militaryForm[2].slice(2 + unit.length, 15)
            + strNum
            + militaryForm[2].slice(15 + strNum.length, militaryForm[2].length);
    }
    militaryForm.splice(2, 1);
    renderedObjects["militaryForm"] = new AsciiObj(militaryForm, 3, 24, 15, null);
}

function build(buildingId) {
    var buildingInfo = currentGameState.constants.buildings[buildingId];
    if (!buildingInfo) {
        console.log("wrong buildingID: " + buildingId);
        return;
    }
    var city = getCity(currentGameState, currentCityCoords);
    var buildingLvl = city.buildings[buildingId];
    if (!buildingLvl) buildingLvl = 0;
    var costs = buildingInfo.levels[String(buildingLvl + 1)].costs;
    var buildFormText = generateBuildForm(costs.Food, costs.Wood, costs.Stone, costs.Gold);
    renderedObjects["buildForm"] = new AsciiObj(buildFormText, 37, city1.length, 10, null);
    var YESprops = new AsciiObjProps("YES", "doBuild");
    var NOprops = new AsciiObjProps("NO", "doBuild");

    var buildMessage;
    buildingInProcess = {name: buildingId, level: buildingLvl + 1};
    if (buildingLvl === 0) {
        buildMessage = "build " + buildingId;
    } else {
        buildMessage = "upgrade " + buildingId + " to level " + (buildingLvl + 1);
    }
    renderedObjects["buildMessage"] = new AsciiObj([buildMessage], 38, 19, 15, null);
    renderedObjects["YESbutton"] = new AsciiObj(["YES"], 49, 25, 15, YESprops);
    renderedObjects["NObutton"] = new AsciiObj(["NO"], 55, 25, 15, NOprops);
}

function doBuild(decision) {
    delete renderedObjects["NObutton"];
    delete renderedObjects["YESbutton"];
    delete renderedObjects["buildMessage"];
    delete renderedObjects["buildForm"];
    if (decision == "NO") return;
    console.log(buildingInProcess.name);
    console.log(buildingInProcess.level);
    console.log(currentCityCoords);
    stompClient.send("/marauders/commands/build", {},
        JSON.stringify(
            {
                "buildingName": String(buildingInProcess.name),
                "buildingLevel": String(buildingInProcess.level),
                "cityCoordinates": currentCityCoords,
                "gameName": "1"
            })
    );
}

function doRecruit(unit) {
    var armyToRecruit = {};
    armyToRecruit[unit] = 1;

    stompClient.send("/marauders/commands/recruit", {},
        JSON.stringify(
            {
                "armyToRecruit": armyToRecruit,
                "cityCoordinates": currentCityCoords,
                "gameName": "1"
            })
    );
}

function main() {
    showText(render(background, renderedObjects));
    clouds.forEach(function (cloud) {
        cloud.xPos = cloud.xPos + 1;
        if (cloud.xPos > background[0].length) cloud.xPos = -cloud.text[0].length
    });
}

setTimeout(function () {
    var socket = new SockJS('http://localhost:8080/game');
    stompClient = Stomp.over(socket);
    mainLoop();
}, 1000);

function mainLoop() {
    connect();
    renderWorldMap();
    setInterval(main, 300)
}

function renderBuildings() {
    var city = getCity(currentGameState, currentCityCoords);
    var buildingSiteUsed;
    var props;
    for (var buildingId in buildingSites) {
        buildingSiteUsed = buildingSites[buildingId];
        var buildingLvl = city.buildings[buildingId];
        var buildingText = getBuildingText(buildingId);
        if (!buildingText) continue;
        if (buildingLvl) {
            props = new AsciiObjProps(buildingId, "build");
            renderedObjects[buildingId] = new AsciiObj(buildingText, buildingSiteUsed.x, buildingSiteUsed.y, 10, props);
        } else {
            props = new AsciiObjProps(buildingId, "build");
            renderedObjects[buildingId] = new AsciiObj(buildButton, buildingSiteUsed.x, buildingSiteUsed.y, 10, props);
        }
    }
}

function resetViews() {
    renderedObjects = {};
    refreshTargets = [];
}

function renderMilitaryPanel() {
    resetViews();
    refreshTargets.push(renderResources);
    refreshTargets.push(fillMilitaryPanel);

    var backButtonProps = new AsciiObjProps(currentCityCoords, "goToCity");
    renderedObjects = {};
    renderedObjects["backButton"] = new AsciiObj(backButton, 0, 18, 15, backButtonProps);
}

function fillMilitaryPanel() {
    var militaryForm = generateMilitaryForm();
    renderedObjects["militaryForm"] = new AsciiObj(militaryForm, 3, 24, 15, null);
    var units = getUnits(currentGameState, currentCityCoords, playerID) || {};
    console.log(units);
    var count = 0;
    for (unit in getAllUnits(currentGameState)) {
        count++;
        var numOfUnit = units[unit] || 0;
        var strNum = String(numOfUnit);
        var newLine = militaryForm[2].slice(0, 2)
            + unit
            + militaryForm[2].slice(2 + unit.length, 15)
            + strNum
            + militaryForm[2].slice(15 + strNum.length, militaryForm[2].length);
        militaryForm.splice(2 + count, 0, newLine);

        var prop = new AsciiObjProps(unit, "doRecruit");
        renderedObjects[unit + "RecruitButton"] = new AsciiObj(["RECRUIT"], 3 + militaryForm[0].length - 8, count + 25, 20, prop   );
    }
    militaryForm.splice(2, 1);
}