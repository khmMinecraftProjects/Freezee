package me.khmdev.Freezee.Game;

import me.khmdev.APIBase.API;
import me.khmdev.APIGames.Auxiliar.ConstantesGames.Equipo;
import me.khmdev.APIGames.Auxiliar.IJugador;
import me.khmdev.APIGames.Games.IGame;
import me.khmdev.APIGames.Partidas.PartidaTCT;

public class PartidaFreezee extends PartidaTCT {

	public PartidaFreezee( String s,IGame game2, API ap) {
		super(s, game2, ap);
	}

	@Override
	public void setEquipo(IJugador j, Equipo e) {
		
	}

}
