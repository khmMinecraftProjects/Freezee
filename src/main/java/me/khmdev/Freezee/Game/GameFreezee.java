package me.khmdev.Freezee.Game;

import me.khmdev.APIBase.API;
import me.khmdev.APIGames.Games.Game;
import me.khmdev.APIGames.Partidas.IPartida;

public class GameFreezee extends Game{

	public GameFreezee(API ap, String nam, String string) {
		super(ap, nam, string);
	}

	@Override
	public void iniListen() {
		
	}

	@Override
	public IPartida NPartida(String s) {
		return new PartidaFreezee(s,this,api);
	}

}
