package me.khmdev.Freezee.Game;

import me.khmdev.APIGames.Auxiliar.Jugador;
import me.khmdev.Freezee.Game.PartidaFreezee.TipoJugador;

public class JugadorFreezee extends Jugador {
	private TipoJugador tipo=TipoJugador.Normal;

	public TipoJugador getTipo() {
		return tipo;
	}

	public void setTipo(TipoJugador tipo) {
		this.tipo = tipo;
	}
	

}
