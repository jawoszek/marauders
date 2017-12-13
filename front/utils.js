var resourceTab=[
    "/==========================\\",
    "|food :                    |",
    "|wood :                    |",
    "|stone:                    |",
    "|gold :                    |",
    "\\==========================/"];
var build_form=[
    "/==================================\\",
    "|                                  |",
    "|food :                            |",
    "|wood :                            |",
    "|stone:                            |",
    "|gold :                            |",
    "|           ___   __               |",
    "|   BUILD  [YES]/[NO]              |",
    "\\==================================/"];
var garrisonTab=[
    "/===============================\\",
    "|         GARRISON              |",
    "|             :                 |",
    "\\===============================/"];

var militaryTab=[
    "/===============================\\",
    "|         YOUR UNITS            |",
    "|             :              [R]|",
    "\\===============================/"];

var militaryToSendTab=[
    "/===============================\\",
    "|       UNITS TO SEND           |",
    "|             :                 |",
    "\\===============================/"];
var xResourceOfset =7;
function showText(text){
	a.text("");
	for (lineNum in text){
		a.append(text[lineNum]);
		a.append("<br/>");
	}
}
function spanForProps(objProps){
	if(!objProps) return "";
	if(!objProps.id && !objProps.funcName) return "";
	if(objProps.id)
		return '<span id="'+objProps.id+'" onClick="'+objProps.funcName+'(this.id)" onmouseover="" style="cursor: pointer;">';
	return '<span onClick="'+objProps.funcName+'()" onmouseover="" style="cursor: pointer;">';
}
function generateResourceForm(food,wood,stone,gold){
	var res = new Array(resourceTab.length);
	for(var i=0 ; i<resourceTab.length ; i++){
		res[i] = resourceTab[i];
	}
	var foodStr = food ? String(food) : "0";
	res[1] = res[1].slice(0,xResourceOfset)+foodStr+res[1].slice(xResourceOfset+foodStr.length,res[1].length);
    var woodStr = wood ? String(wood) : "0";
    res[2] = res[2].slice(0,xResourceOfset)+woodStr+res[2].slice(xResourceOfset+woodStr.length,res[2].length);
    var stoneStr = stone ? String(stone) : "0";
    res[3] = res[3].slice(0,xResourceOfset)+stoneStr+res[3].slice(xResourceOfset+stoneStr.length,res[3].length);
    var goldStr = gold ? String(gold) : "0";
    res[4] = res[4].slice(0,xResourceOfset)+goldStr+res[4].slice(xResourceOfset+goldStr.length,res[4].length);
    return res;
}
function generateBuildForm(food,wood,stone,gold){
    var res = new Array(build_form.length);
    for(var i=0 ; i<build_form.length ; i++){
        res[i] = build_form[i];
    }
    var foodStr = food ? String(food) : "0";
    res[2] = res[2].slice(0,xResourceOfset)+foodStr+res[2].slice(xResourceOfset+foodStr.length,res[2].length);
    var woodStr = wood ? String(wood) : "0";
    res[3] = res[3].slice(0,xResourceOfset)+woodStr+res[3].slice(xResourceOfset+woodStr.length,res[3].length);
    var stoneStr = stone ? String(stone) : "0";
    res[4] = res[4].slice(0,xResourceOfset)+stoneStr+res[4].slice(xResourceOfset+stoneStr.length,res[4].length);
    var goldStr = gold ? String(gold) : "0";
    res[5] = res[5].slice(0,xResourceOfset)+goldStr+res[5].slice(xResourceOfset+goldStr.length,res[5].length);
    return res;
}

function generateGarrisonForm(units){
    var garrison = new Array(garrisonTab.length);
    for(var i=0 ; i<garrisonTab.length ; i++)
        garrison[i] = garrisonTab[i];
    for (unit in units) {
        var numOfUnit = units[unit];
        var strNum = String(numOfUnit);
        garrison.splice(3, 0, garrison[2]);
        garrison[3] = garrison[2].slice(0, 2)
            + unit
            + garrison[2].slice(2 + unit.length, 15)
            + strNum
            + garrison[2].slice(15 + strNum.length, garrison[2].length);
    }
    garrison.splice(2, 1);
    return garrison;
}

function generateUnitsToSendForm(){
    var res = new Array(militaryToSendTab.length);
    for(var i=0 ; i<militaryToSendTab.length ; i++)
        res[i] = militaryToSendTab[i];
    return res;
}


function generateMilitaryForm(){
    var res = new Array(militaryTab.length);
    for(var i=0 ; i<militaryTab.length ; i++)
        res[i] = militaryTab[i];
    return res;
}