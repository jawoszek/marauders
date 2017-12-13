var a = $("#game");
var renderedObjects = {};
var refreshTargets = [];
var clouds = [];
var background;
var playerID = "Rudy";
var currentCityCoords;
var tempCityCoords;
var cloud1;
var cloud2;
var cloud3;
var buildingInProcess;
var currentGameState;
var stompClient;
var currentArmyToSend;

var buildingSites = {
    "Farm": {x: 39, y: 3},
    "City Hall": {x: 30, y: 6},
    "Woodcutters Camp": {x: 65, y: 4},
    "Fishing Port": {x: 62, y: 10},
    "Quarry": {x: 0, y: 7}
};

function goToWorldMap(){
    currentCityCoords = null;
    resetViews();
    refreshTargets.push(renderResources);
    background = backgroundHuge;
    renderWorldMapAtPos({x:0,y:0,z:1});
}

function goToWorldTargetMap(){
    resetViews();
    refreshTargets.push(renderResources);
    background = backgroundHuge;
    renderWorldTargetMapAtPos({x:0,y:0,z:1});
}

function hiCloud(id) {
    console.log("it's me a " + id);
}

function goToCity(coords) {
    tempCityCoords = null;
    resetViews();
    refreshTargets.push(renderBuildings);
    refreshTargets.push(renderResources);
    refreshTargets.push(renderGarrison);
    currentCityCoords = coords;
    background = backgroundBig;
    renderedObjects["cloud1"] = cloud1;
    renderedObjects["cloud2"] = cloud2;
    renderedObjects["cloud3"] = cloud3;
    renderAsciiAtPos({x:0,y:0,z:0},city1,"city");
    renderButtonAtPos({x:0,y:18,z:15},"backButton",backButton,"goToWorldMap");
    renderButtonAtPos({x:68,y:20,z:15},"militaryButton",attackButton,"renderMilitaryPanel");
}

function renderResources() {
    var resources = getPlayerResources(currentGameState, playerID);
    var resourceText = generateResourceForm(resources.Food, resources.Wood, resources.Stone, resources.Gold);
    if (currentCityCoords)
        renderAsciiAtPos({x:backButton[0].length,y:city1.length,z:10},resourceText,"resources");
    else
        renderAsciiAtPos({x:backButton[0].length,y:world.length,z:10},resourceText,"resources");
}

function renderGarrison() {
    var units = getUnits(currentGameState, currentCityCoords, playerID);
    var militaryForm = generateGarrisonForm(units);
    renderAsciiAtPos({x:3,y:24,z:15},militaryForm,"militaryForm");
}

function build(buildingId) {
    var buildingInfo = currentGameState.constants.buildings[buildingId];
    if (!buildingInfo) {
        console.log("wrong buildingID: " + buildingId);
        return;
    }
    var city = getCity(currentGameState, currentCityCoords);
    var buildingLvl = city.buildings[buildingId] || 0;
    var costs = buildingInfo.levels[String(buildingLvl + 1)].costs;
    var buildFormText = generateBuildForm(costs.Food, costs.Wood, costs.Stone, costs.Gold);
    renderBuildFormAtPos({x:37,y:city1.length,z:10},buildingId,buildingLvl,buildFormText);
}

function doBuild(decision) {
    if (decision == "YES"){
        sendBuildCommand(currentCityCoords,buildingInProcess);
    }
    delete  renderedObjects["buildForm"];
    delete renderedObjects["buildMessage"];
    delete renderedObjects["YESbutton"];
    delete renderedObjects["NObutton"];
}

function increaseSend(unit){
    console.log("increased: "+unit + " to: " + currentArmyToSend.armyToSend[unit]);
    if(!currentArmyToSend.armyToSend[unit])
        currentArmyToSend.armyToSend[unit] = 1;
    else
        currentArmyToSend.armyToSend[unit] = currentArmyToSend.armyToSend[unit]+1;
}
function decreaseSend(unit){
    console.log("decreased: "+unit + " to: " + currentArmyToSend.armyToSend[unit]);
    if(currentArmyToSend.armyToSend[unit] && currentArmyToSend.armyToSend[unit]>0)
        currentArmyToSend.armyToSend[unit] = currentArmyToSend.armyToSend[unit]-1;
}

function doRecruit(unit) {
    sendRecruitCommand(unit,tempCityCoords);
}

function doSendArmy(coordinates){
    currentArmyToSend["targetCoordinates"] = coordinates;
    moveArmyCommand(currentArmyToSend);
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
            renderButtonAtPos({x:buildingSiteUsed.x,y:buildingSiteUsed.y,z:10},buildingId,buildingText,"build");
        } else {
            renderButtonAtPos({x:buildingSiteUsed.x,y:buildingSiteUsed.y,z:10},buildingId,buildButton,"build");
        }
    }
}

function resetViews() {
    renderedObjects = {};
    refreshTargets = [];
}

function renderMilitaryPanel() {
    resetViews();
    background = backgroundHuge;
    refreshTargets.push(renderResources);
    refreshTargets.push(fillMilitaryPanel);
    tempCityCoords = currentCityCoords;
    var backButtonProps = new AsciiObjProps(currentCityCoords, "goToCity");
    currentCityCoords = null;
    renderedObjects["backButtonTarget"] = new AsciiObj(backButton, 0, 18, 15, backButtonProps);

    //renderButtonAtPos({x:0,y:18,z:15},currentCityCoords,backButton,"goToCity");

    currentArmyToSend = {};
    currentArmyToSend["sourceCoordinates"] = tempCityCoords;
    currentArmyToSend["armyToSend"] = {"Levy":1};
    currentArmyToSend["gameName"] = 1;
    renderWorldTargetMapAtPos({x:0,y:0,z:1});
    //renderButtonAtPos({x:20,y:14,z:15},"targetMapButton",["SEND 1 LEVY"],"goToWorldTargetMap");
}

function fillMilitaryPanel() {
    var militaryForm = generateMilitaryForm();
    var mFormPos ={x:40,y:28,z:15};
    var units = getUnits(currentGameState, tempCityCoords, playerID) || {};
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
        var buttonPos = {x:mFormPos.x+militaryForm[0].length - 10,y:count + mFormPos.y+1,z:mFormPos.z+1};
        renderButtonAtPos(buttonPos,unit,["[RECRUIT]"],"doRecruit");
    }
    militaryForm.splice(2, 1);
    renderAsciiAtPos(mFormPos,militaryForm,"militaryForm");

    var mToSendFormPos ={x:75,y:28,z:15};
    var militaryToSendForm = generateUnitsToSendForm();
    count = 0;
    for (unit in getAllUnits(currentGameState)) {
        count++;
        var numOfUnit = units[unit] || 0;
        var strNum = String(currentArmyToSend.armyToSend[unit] || 0);
        var newLine = militaryToSendForm[2].slice(0, 2)
            + unit
            + militaryToSendForm[2].slice(2 + unit.length, 15)
            + strNum
            + militaryToSendForm[2].slice(15 + strNum.length, militaryToSendForm[2].length);
        militaryToSendForm.splice(2 + count, 0, newLine);
        var buttonPos = {x:mToSendFormPos.x+militaryToSendForm[0].length - 10,y:count + mToSendFormPos.y+1,z:mToSendFormPos.z+1};

        var plusButtonProps = new AsciiObjProps(unit, "increaseSend");
        renderedObjects[unit+"+"] = new AsciiObj(["[+]"], buttonPos.x, buttonPos.y, buttonPos.z, plusButtonProps);
        var minusButtonProps = new AsciiObjProps(unit, "decreaseSend");
        renderedObjects[unit+"-"] = new AsciiObj(["[-]"], buttonPos.x+4, buttonPos.y, buttonPos.z, minusButtonProps);
    }
    militaryToSendForm.splice(2, 1);
    renderAsciiAtPos(mToSendFormPos,militaryToSendForm,"militaryToSendForm");
}

function main() {
    showText(render(background, renderedObjects));
    clouds.forEach(function (cloud) {
        cloud.xPos = cloud.xPos + 1;
        if (cloud.xPos > background[0].length) cloud.xPos = -cloud.text[0].length
    });
}

function connect() {
    stompClient.connect('', '', function (frame) {
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
    refreshTargets.forEach(function(target){
        target();
    });
}

setTimeout(function () {
    var socket = new SockJS('http://localhost:8080/game');
    stompClient = Stomp.over(socket);
    cloud1 = new AsciiObj(cloudBig, 1, 2, 10, new AsciiObjProps("cloud1", "hiCloud"));
    cloud2 = new AsciiObj(cloudMed, 25, 2, 10, new AsciiObjProps("cloud2", "hiCloud"));
    cloud3 = new AsciiObj(cloudSmall1, 45, 1, 10, new AsciiObjProps("cloud3", "hiCloud"));
    clouds = [cloud1, cloud2, cloud3];
    mainLoop();
}, 1000);

function mainLoop() {
    connect();
    goToWorldMap();
    setInterval(main, 300)
}