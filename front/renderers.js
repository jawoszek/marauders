function renderWorldMapAtPos(position) {
    var worldObj = new AsciiObj(world, position.x, position.y, position.z, null);
    var cityObjOnMap1 =
        new AsciiObj(cityOnMap, position.x+95, position.y+18, position.z+1, new AsciiObjProps("(2, 5)", "goToCity"));
    var cityObjOnMap2 =
        new AsciiObj(cityOnMap2, position.x+11, position.y+15, position.z+1, new AsciiObjProps("(4, 3)", "goToCity"));
    renderedObjects["world"] = worldObj;
    renderedObjects["cityOnMap1"] = cityObjOnMap1;
    renderedObjects["cityOnMap2"] = cityObjOnMap2;
}

function renderWorldTargetMapAtPos(position){
    var worldObj = new AsciiObj(world, position.x, position.y, position.z, null);
    var attackTarget1 =
        new AsciiObj(mapAttackTarget, position.x+94, position.y+17, position.z+1, null);
    var cityObjOnMap1 =
        new AsciiObj(cityOnMap, position.x+95, position.y+18, position.z+2, new AsciiObjProps("(2, 5)", "doSendArmy"));
    var attackTarget2 =
        new AsciiObj(mapAttackTarget, position.x+10, position.y+14, position.z+1, null);
    var cityObjOnMap2 =
        new AsciiObj(cityOnMap2, position.x+11, position.y+15, position.z+2, new AsciiObjProps("(4, 3)", "doSendArmy"));
    renderedObjects["world"] = worldObj;
    renderedObjects["attackTarget1"] = attackTarget1;
    renderedObjects["cityOnMap1"] = cityObjOnMap1;
    renderedObjects["attackTarget2"] = attackTarget2;
    renderedObjects["cityOnMap2"] = cityObjOnMap2;
}

function renderBuildFormAtPos(position,buildingId,buildingLvl,buildFormText){
    renderedObjects["buildForm"] = new AsciiObj(buildFormText, position.x, position.y, position.z, null);
    var YESprops = new AsciiObjProps("YES", "doBuild");
    var NOprops = new AsciiObjProps("NO", "doBuild");

    var buildMessage;
    buildingInProcess = {name: buildingId, level: buildingLvl + 1};
    if (buildingLvl === 0) {
        buildMessage = "build " + buildingId;
    } else {
        buildMessage = "upgrade " + buildingId + " to level " + (buildingLvl + 1);
    }
    renderedObjects["buildMessage"] = new AsciiObj([buildMessage], position.x+1, position.y+1, position.z+1, null);
    renderedObjects["YESbutton"] = new AsciiObj(["YES"], position.x+12, position.y+7, position.z+1, YESprops);
    renderedObjects["NObutton"] = new AsciiObj(["NO"], position.x+18, position.y+7, position.z+1, NOprops);
}


function renderAsciiAtPos(position,ascii,id){
    renderedObjects[id] = new AsciiObj(ascii, position.x, position.y, position.z, null);
}

function renderButtonAtPos(position,buttonId,buttonAscii,buttonFunction){
    var buttonProps = new AsciiObjProps(buttonId, buttonFunction);
    renderedObjects[buttonId] =
        new AsciiObj(buttonAscii, position.x, position.y, position.z, buttonProps);
}
