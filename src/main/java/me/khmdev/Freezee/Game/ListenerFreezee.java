package me.khmdev.Freezee.Game;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.khmdev.APIGames.Auxiliar.Jugador;
import me.khmdev.APIGames.Auxiliar.ListenerGames;
import me.khmdev.APIGames.Games.IGame;

public class ListenerFreezee extends ListenerGames {

	public ListenerFreezee(IGame plug) {
		super(plug);
	}

	@Override
	protected void spawn(Jugador j, PlayerRespawnEvent event) {

	}

	@Override
	protected void death(Jugador j, EntityDeathEvent event) {

	}

}
