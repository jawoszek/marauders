function mockInit(){
	resources = new Resources(100,150,50,10);
	buildings=[];
	building["cityHall"] = 1;
	playerCity = new PlayerCity(1,buildings);
	playerState = new PlayerState(1, playerCity, resources);
	playersState = [];
	playersState[1] = playerState;
	return new GameState(playerState,{})
}
function citysBuildingStites(){
	
}