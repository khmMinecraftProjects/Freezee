package me.khmdev.Freezee.Game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.khmdev.APIAuxiliar.ScoreBoard.Functor;
import me.khmdev.APIAuxiliar.ScoreBoard.FunctorString;
import me.khmdev.APIAuxiliar.ScoreBoard.ObjetiveData;
import me.khmdev.APIAuxiliar.ScoreBoard.getConstant;
import me.khmdev.APIAuxiliar.ScoreBoard.getStringConst;
import me.khmdev.APIGames.Scores.BoardGames;

public class BoardFreezee extends BoardGames {

	public BoardFreezee(final PartidaFreezee partida) {
		super(partida);
		add(new ObjetiveData(new getStringConst(ChatColor.LIGHT_PURPLE+"Congelados: "),
				new Functor() {
					
					@Override
					public int getInt(Player p) {
						return partida.getCongelados();
					}
				}));
		add(new ObjetiveData(new getStringConst(ChatColor.RED+"Sin congelar: "),
				new Functor() {
					
					@Override
					public int getInt(Player p) {
						return partida.getNJug()-partida.getCongelados()-1;
					}
				}));
		
		add(new ObjetiveData(null,new getConstant(-1)));
		add(new ObjetiveData(new FunctorString() {
			
			@Override
			public String getString(Player p) {
				return ((JugadorFreezee) partida
						.getJugador(p.getName())).getTipo()+"";
			}
		},
				new getConstant(-2)));
	
		
		add(new ObjetiveData(null,new getConstant(-4)));
		add(new ObjetiveData(new getStringConst(ChatColor.GREEN+"Finaliza en:"),
				new getConstant(-5)));
		add(new ObjetiveData(new FunctorString() {
			
			@Override
			public String getString(Player p) {
				return ChatColor.GREEN+partida.getStringTime();
			}
		},
				new getConstant(-6)));
	}
	
	public void addScoreBoard(Player pl) {
		pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
