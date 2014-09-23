package me.khmdev.Freezee.Game;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
import me.khmdev.Freezee.base;
import me.khmdev.Freezee.Items.Congelador;
import me.khmdev.Freezee.Items.Descongelador;

public class PartidaFreezee extends PartidaTCT {

	public enum TipoJugador {
		Congelador, Congelado, Normal
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

	public void spawnCongelado(final Jugador j,final int t){
		ListenerFreeze.addPlayer(j.getPlayer().getName(), t*1000);
		j.getPlayer().getInventory().setHelmet(new ItemStack(Material.ICE));

		Bukkit.getServer().getScheduler()
		.runTaskLater(base.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int e=t*20;
				if(t<0){
					e=Integer.MAX_VALUE;
				}
				j.getPlayer().addPotionEffect(
						new PotionEffect(PotionEffectType.BLINDNESS, e, 5));
			}
		}, 1);
	}
	public void spawnDesCongelado(Jugador j){
		ListenerFreeze.removePlayer(j.getPlayer().getName());
		j.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
		j.getPlayer().getInventory().setHelmet(null);

	}

	@Override
	public void iniControl() {
		control = new ControlFreezee(this, game);
	}

	@Override
	public void iniciada() {
		congelados = 0;
		ultimo = System.currentTimeMillis();
		for (int z = 0; z < numCongeladores; z++) {
			int i = (int) (Math.random() * jugadores.size() - 0.5);
			for (IJugador j : jugadores.values()) {
				if (i == 0) {
					((JugadorFreezee) j).setTipo(TipoJugador.Congelador);
				}
				i--;
			}
		}
		numCongeladores=numCongeladoresFinal;
		
		super.iniciada();

	}

	private int seconds = 10;

	@SuppressWarnings("deprecation")
	@Override
	public void Equipar(Jugador j) {
		j.getPlayer().getInventory().clear();
		if (((JugadorFreezee) j).getTipo() == TipoJugador.Congelador) {
			spawnCongelado(j,seconds);
			j.getPlayer().getInventory().addItem(congela.getItem());
			j.getPlayer().getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			j.getPlayer().getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			j.getPlayer().getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			j.getPlayer().updateInventory();
		} else {
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
			if (((JugadorFreezee) j).getTipo() == TipoJugador.Congelador) {
				((JugadorFreezee) j).setGanador(1);
			} else {
				((JugadorFreezee) j).setGanador(0);
			}
		}
		Gofinalizar();
	}

	private void PierdeEnemigo() {
		for (IJugador j : jugadores.values()) {
			if (((JugadorFreezee) j).getTipo() == TipoJugador.Congelador) {
				((JugadorFreezee) j).setGanador(0);
			} else {
				((JugadorFreezee) j).setGanador(1);
			}
		}
		Gofinalizar();
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
			ultimo = System.currentTimeMillis();
			atacado.getPlayer().getInventory().clear();
			atacad.updateInventory();
			spawnCongelado(atacado,-1);
			atacante.setPuntuacion(atacante.getPuntuacion() + 1);
			return;
		}
		atacante.getPlayer().sendMessage(atacado + " ya esta congelado");

	}

	public void desCongela(Player atacant, Player atacad) {
		JugadorFreezee atacante = (JugadorFreezee) getJugador(atacant.getName()), atacado = (JugadorFreezee) getJugador(atacad
				.getName());
		if (atacado.getTipo() == TipoJugador.Congelado) {
			atacado.setTipo(TipoJugador.Normal);
			congelados--;
			sendAll(atacante + " a descongelado a " + atacado);
			ListenerFreeze.removePlayer(atacad.getName());
			spawnDesCongelado(atacado);

			Equipar(atacado);
			atacante.setPuntuacion(atacante.getPuntuacion() + 1);
			atacado.getPlayer().getInventory().setHelmet(null);

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
		pl.sendMessage(v + "Coins por kill:           " + (j.getKills())
				+ "x2=+" + (j.getKills() * 2));
		coins += j.getKills() * 2;

		pl.sendMessage(v + "Coins por muerte:  " + (j.getDeaths()) + "x(-1)=-"
				+ (j.getDeaths()));
		coins -= j.getDeaths();
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

	public void sendAsToTeam(Jugador j, String message) {
		TipoJugador t = ((JugadorFreezee) j).getTipo();
		message = Lang
				.get("send_selective_team")
				.replace("%clr%", ""+(((JugadorFreezee) j).getTipo()==
				TipoJugador.Congelador?ChatColor.BLUE:ChatColor.RED))
				.replace("%Player%", j.getPlayer().getName())
				.replace("%msg%",
						ChatColor.translateAlternateColorCodes('&', message));
		for (IJugador jj : jugadores.values()) {
			if (((JugadorFreezee) jj).getTipo() == t) {
				jj.getPlayer().sendMessage(ChatColor.ITALIC + message);
			}
		}
	}
	
	public void sendAsToAll(Jugador j, String message) {
		Iterator<Entry<String, IJugador>> jj = jugadores.entrySet().iterator();
		message = Lang
				.get("send_selective_all")
				.replace("%clr%", ""+(((JugadorFreezee) j).getTipo()==
				TipoJugador.Congelador?ChatColor.BLUE:ChatColor.RED))
				.replace("%Player%", j.getPlayer().getName())
				.replace("%msg%",
						ChatColor.translateAlternateColorCodes('&', message));
		while (jj.hasNext()) {

			jj.next().getValue().getPlayer().sendMessage(message);
		}
	}
	
	private int numCongeladoresFinal=1;
	private int numCongeladores=numCongeladoresFinal;

	public boolean setTipo(JugadorFreezee j, TipoJugador equip) {
		if(j.getTipo()==TipoJugador.Congelador){
			numCongeladores--;
		}
		if(equip==TipoJugador.Congelador){
			if (numCongeladores!=0) {
				j.setTipo(equip);
				numCongeladores--;
				return true;
			}else{
				return false;
			}
		}
		j.setTipo(equip);
		return true;

	}
	@Override
	public void cargaConf(ConfigurationSection section) {
		if(section.isInt("Congeladores")){
			numCongeladoresFinal=section.getInt("Congeladores");
		}else{
			section.set("Congeladores", numCongeladoresFinal);
		}
		if(section.isInt("TiempoSiguientea")){
			tiempoLimite=section.getInt("TiempoSiguiente")*1000;
		}else{
			section.set("TiempoSiguiente", tiempoLimite/1000);
		}
		super.cargaConf(section);
	}

	public void spawnCongelado(Jugador j) {
		spawnCongelado(j, seconds);
	}
}
