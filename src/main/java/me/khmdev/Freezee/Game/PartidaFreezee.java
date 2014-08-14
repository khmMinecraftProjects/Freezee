package me.khmdev.Freezee.Game;

import org.bukkit.entity.Player;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIBase.API;
import me.khmdev.APIGames.Auxiliar.ConstantesGames.Equipo;
import me.khmdev.APIGames.Auxiliar.IJugador;
import me.khmdev.APIGames.Auxiliar.Jugador;
import me.khmdev.APIGames.Auxiliar.Variables;
import me.khmdev.APIGames.Games.IGame;
import me.khmdev.APIGames.Partidas.PartidaTCT;
import me.khmdev.APIGames.Scores.BoardGames;
import me.khmdev.APIGames.lang.Lang;
import me.khmdev.Freezee.Items.Congelador;
import me.khmdev.Freezee.Items.Descongelador;

public class PartidaFreezee extends PartidaTCT {

	public enum TipoJugador {
		Enemigo, Congelado, Normal
	};

	private int congelados = 0;
	private Congelador congela;
	private Descongelador descongela;
	private long ultimo = 0, tiempoLimite = 120000;

	public PartidaFreezee(String s, IGame game2, API ap) {
		super(s, game2, ap);
		congela = new Congelador(this);
		descongela = new Descongelador(this);
		CItems.addItem(descongela);
		CItems.addItem(congela);
	}
	@Override
	public void iniControl() {
		control = new ControlFreezee(this, game);
	}
	@Override
	public void iniciada() {
		congelados = 0;
		ultimo = System.currentTimeMillis();
		int i = (int) (Math.random() * jugadores.size() - 0.5);

		for (IJugador j : jugadores.values()) {
			if (i == 0) {
				((JugadorFreezee) j).setTipo(TipoJugador.Enemigo);
			}
			i--;
		}
		super.iniciada();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void Equipar(Jugador j) {
		// super.Equipar(j);

		if (((JugadorFreezee) j).getTipo() == TipoJugador.Enemigo) {
			j.getPlayer().getInventory().addItem(congela.getItem());
			j.getPlayer().updateInventory();
		}else{
			j.getPlayer().getInventory().addItem(descongela.getItem());
			j.getPlayer().updateInventory();
		}
	}

	@Override
	public BoardGames getScore() {
		return new BoardFreezee(this);
	}

	public Jugador newIJ() {
		return new JugadorFreezee();
	};

	public int getCongelados() {
		return congelados;
	}

	public String getStringTime() {

		long t = tiempoParaGanar();
		int min = ((int) ((t / 1000) / 60)), sec = ((int) ((t / 1000) % 60));
		return (min > 9 ? min : "0" + min) + ":" + (sec > 9 ? sec : "0" + sec);
	}

	@Override
	public void setEquipo(IJugador j, Equipo e) {

	}


	public void comprobaciones() {
		if (jugadores.size() == congelados + 1) {
			GanaEnemigo();
		} else if (tiempoParaGanar() <= 0) {
			PierdeEnemigo();
		}
	}

	private void GanaEnemigo() {
		for (IJugador j : jugadores.values()) {
			if (((JugadorFreezee) j).getTipo() == TipoJugador.Enemigo) {
				((JugadorFreezee) j).setGanador(1);
			} else {
				((JugadorFreezee) j).setGanador(0);
			}
		}
		finalizar();
	}

	private void PierdeEnemigo() {
		for (IJugador j : jugadores.values()) {
			if (((JugadorFreezee) j).getTipo() == TipoJugador.Enemigo) {
				((JugadorFreezee) j).setGanador(0);
			} else {
				((JugadorFreezee) j).setGanador(1);
			}
		}
		finalizar();
	}

	private long timeUltimo() {
		return System.currentTimeMillis() - ultimo;
	}

	private long tiempoParaGanar() {
		return tiempoLimite - timeUltimo();
	}

	public void JugadorAbandona(Jugador j) {
		super.JugadorAbandona(j);
		ListenerFreeze.removePlayer(j.getPlayer().getName());
	}
	
	@SuppressWarnings("deprecation")
	public void congela(Player atacant, Player atacad) {
		JugadorFreezee atacante = (JugadorFreezee) getJugador(atacant.getName()), atacado = (JugadorFreezee) getJugador(atacad
				.getName());
		if (atacado.getTipo() == TipoJugador.Normal) {
			atacado.setTipo(TipoJugador.Congelado);
			congelados++;
			sendAll(atacante + " a congelado a " + atacado);
			ListenerFreeze.addPlayer(atacad.getName(), -1);
			ultimo = System.currentTimeMillis();
			atacad.getInventory().remove(descongela.getItem());
			atacad.updateInventory();
			atacante.setPuntuacion(atacante.getPuntuacion()+1);
			return;
		}
		atacante.getPlayer().sendMessage(atacado + " ya esta congelado");

	}
	
	@SuppressWarnings("deprecation")
	public void desCongela(Player atacant, Player atacad) {
		JugadorFreezee atacante = (JugadorFreezee) getJugador(atacant.getName()), atacado = (JugadorFreezee) getJugador(atacad
				.getName());
		if (atacado.getTipo() == TipoJugador.Congelado) {
			atacado.setTipo(TipoJugador.Normal);
			congelados--;
			sendAll(atacante + " a descongelado a " + atacado);
			ListenerFreeze.removePlayer(atacad.getName());
			atacad.getInventory().addItem(descongela.getItem());
			atacad.updateInventory();
			atacante.setPuntuacion(atacante.getPuntuacion()+1);
			return;
		}
		atacante.getPlayer().sendMessage(atacado + " no esta congelado");

	}
	
	
	
	protected int calcularCoins(Jugador j) {
		int coins = 0;
		Player pl = j.getPlayer();
		String v = Variables.ChatColorStandar;
		pl.sendMessage(v + "/--------------------------------\\");
		pl.sendMessage(v + "Coins por participar:        +5");
		coins += 5;
		pl.sendMessage(v + "Coins por punto:       " + (j.getPuntuacion())
				+ "x3=+" + (j.getPuntuacion() * 3));
		coins += j.getPuntuacion() * 3;

		if (j.isGanador() == 1) {
			pl.sendMessage(v + "Coins por ganar:            +10");
			coins += 10;
		}
		j.getPlayer().sendMessage(
				v + Lang.get("coins_player").replace("%Coins%", "" + coins));
		pl.sendMessage(v + "\\--------------------------------/");

		return coins;
	}
}
