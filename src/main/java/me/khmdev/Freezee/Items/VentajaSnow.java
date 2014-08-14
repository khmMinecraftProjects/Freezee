package me.khmdev.Freezee.Items;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIGames.Auxiliar.Jugador;
import me.khmdev.APIGames.Books.Ventajas.VentajaInitItem;

public class VentajaSnow extends VentajaInitItem{
	public VentajaSnow(ItemStack it, double p) {
		super(it, p);
	}
	private static HashMap<String, Integer>players=new HashMap<>();
	private final static int num=20;
	public void use(Jugador jugador){
		super.use(jugador);
		players.put(jugador.getPlayer().getName(),0);
	}
	@SuppressWarnings("deprecation")
	protected void eq(Player pl){
		double i=0;
		if(players.containsKey(pl.getName())){
			i=players.get(pl.getName());
		}
		if(i>=num){return;}
		pl.getInventory().remove(ItemOri);
		ItemStack it=ItemOri.clone();
		//it.setAmount(num-i);
		it.setDurability((short) (it.getType().getMaxDurability()
				*(1-(i/num))));
		
		pl.getInventory().addItem(it);
		pl.updateInventory();
	}
	public static int use(String s){
		int i=0;
		if(players.containsKey(s)){
			i=players.get(s);
		}
		i++;
		players.put(s, i);
		return num-i;
	}
	public static int getMax() {
		return num;
	}
}
