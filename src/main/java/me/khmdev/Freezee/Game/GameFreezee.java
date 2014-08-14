package me.khmdev.Freezee.Game;

import org.bukkit.Material;

import me.khmdev.APIBase.API;
import me.khmdev.APIGames.Books.IBook;
import me.khmdev.APIGames.Books.SelectorGame;
import me.khmdev.APIGames.Games.Game;
import me.khmdev.APIGames.Partidas.IPartida;
import me.khmdev.Freezee.Items.Ventajas;

public class GameFreezee extends Game{

	public GameFreezee(API ap, String nam, String string) {
		super(ap, nam, string);
	}

	@Override
	public void iniListen() {
		listener = new ListenerFreezee(this);
		listen();
	}

	public IBook initBook(){
		return new BookFreezee(this);
	}
	
	@Override
	public IPartida NPartida(String s) {
		return new PartidaFreezee(s,this,api);
	}
	
	protected void initVentajas(){
		gestorV=SelectorGame.getGV(this,Material.DIAMOND_SWORD);
		Ventajas.init(gestorV);
	}
	protected void initKits(){

	}
}
