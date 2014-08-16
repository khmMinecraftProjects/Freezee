package me.khmdev.Freezee;

import org.bukkit.plugin.Plugin;

import me.khmdev.APIBase.API;
import me.khmdev.APIGames.APIG;
import me.khmdev.Freezee.Game.GameFreezee;


public class base {
	APIG apig;
	API api;
	private static init instance;
	
	private GameFreezee game;

	public base(init plug){

		api = API.getInstance();
		instance=plug;
		apig = APIG.getInstance();
		game=new GameFreezee(api,"FZ","Freezee");
		apig.newGame(game);

	}

	public static Plugin getInstance() {
		return instance;
	}

}
