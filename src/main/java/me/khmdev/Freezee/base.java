package me.khmdev.Freezee;

import me.khmdev.APIBase.API;
import me.khmdev.APIGames.APIG;
import me.khmdev.Freezee.Game.GameFreezee;


public class base {
	APIG apig;
	API api;
	private GameFreezee game;

	public base(init plug){

		api = API.getInstance();

		apig = APIG.getInstance();
		game=new GameFreezee(api,"FZ","Freezee");
		apig.newGame(game);

	}

}
