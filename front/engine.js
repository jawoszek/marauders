var SPAN_END = "</span>"
function asciiObjsToTable(background,asciiObjects){
	yLen = background.length;
	xLen = background[0].length;
	var objTable = new Array(yLen);
	for(var i=0 ; i<yLen ; i++){
		objTable[i] = new Array(xLen);
	}
	for (asciiObjectNum in asciiObjects){
		asciiObject = asciiObjects[asciiObjectNum];
		props = asciiObject.props;
		objYlen = asciiObject.text.length;
		for(var y=asciiObject.yPos ; y-asciiObject.yPos<objYlen && y<yLen ; y++){
			objxLen = asciiObject.text[y-asciiObject.yPos].length;
			if(y<0){
				continue;
			}
			for(var x=asciiObject.xPos ; x-asciiObject.xPos<objxLen && x<xLen ; x++){
				if(x<0){
					continue;
				}
				var charValAtPos = asciiObject.text[y-asciiObject.yPos][x-asciiObject.xPos];
				if(charValAtPos == '~'){
					if(Math.random()>0.85){
						charValAtPos = ' ';
					}
				}
				if(charValAtPos == '*'){
					if(Math.random()>0.75){
						charValAtPos = ' ';
					}
				}
				if(charValAtPos == '`'){
					continue;
				}
				prevObj = objTable[y][x]
				if(prevObj != null && prevObj.zPos != null & (asciiObject.zPos <= prevObj.zPos)){
					continue;
				}
				objTable[y][x] = {charVal:charValAtPos,zPos:asciiObject.zPos,props:props};
			}
		}
	}
	return objTable;
}

function asciiTableToHTML(background,asciiTable){
	var yLen = background.length;
	var html = new Array(yLen);
	for(var y=0 ; y<yLen ; y++){
		var xLen = background[y].length;
		html[y] = new Array();
		for(var x=0 ; x<xLen ; x++){
			var obj = asciiTable[y][x];
            var charVal;
            var props;
            if(!obj) charVal = " ";
            else {
                charVal = obj.charVal;
                props = obj.props;
            }
			if(props == null || props.id == null || props.funcName == null){
				html[y].push(charVal);
			} else {
				html[y].push(spanForProps(props));
				html[y].push(charVal);
				html[y].push(SPAN_END);
			}	
		}
		html[y] = html[y].join("");
	}
	return html;
}
function AsciiObjProps(id,funcName){
	this.id = id;
	this.funcName = funcName;
}
 
function AsciiObj(text, xPos, yPos, zPos, props){
	this.text = text;
	this.xPos = xPos;
	this.yPos = yPos;
	this.zPos = zPos;
	this.props= props;
}
function render(background,asciiObjects){
	return asciiTableToHTML(background,asciiObjsToTable(background,renderedObjects));
}