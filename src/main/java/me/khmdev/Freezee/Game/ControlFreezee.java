package me.khmdev.Freezee.Game;

import me.khmdev.APIGames.Auxiliar.Control;
import me.khmdev.APIGames.Games.IGame;
import me.khmdev.APIGames.Partidas.Partida;



public class ControlFreezee extends Control {

	public ControlFreezee(Partida p, IGame plu) {
		super(p, plu);
		tineOne=0;
	}
}
