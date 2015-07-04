package me.khmdev.Freezee.Game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomInventory;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.ItemOpenInventory;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIGames.Books.Ventajas.InitBook;
import me.khmdev.APIGames.Games.Game;
import me.khmdev.APIGames.lang.Lang;
import me.khmdev.Freezee.Game.PartidaFreezee.TipoJugador;

public class BookFreezee extends InitBook {
	protected ItemOpenInventory equipo;
	protected CustomInventory inven;
	public BookFreezee(Game g) {
		super(g);
		inven = new CustomInventory(Lang.get("selector_team").replace("%Game%",
				g.getAlias()));

		equipo = new ItemOpenInventory(AuxPlayer.getItem(Material.WOOL,
				Lang.get("select_team")), inven.getInventory());
		inv.addItem(equipo);

		inven.addItem(new setterEquipo(Material.ICE, TipoJugador.Congelador));
		inven.addItem(new setterEquipo(Material.FIRE, TipoJugador.Normal));
		CItems.addInventorys(inven);
	}

	public class setterEquipo extends CustomItem {
		TipoJugador equip;

		public setterEquipo(Material m, TipoJugador eqq) {
			super(AuxPlayer.getItem(m, "Equipo " + eqq));
			equip = eqq;

		}

		@Override
		public void execute(InventoryClickEvent event) {
			@SuppressWarnings("deprecation")
			Player pl = Bukkit.getPlayer(event.getWhoClicked().getName());
			if (pl == null) {
				return;
			}
			if (event.getWhoClicked().hasPermission("setterEquipo.apig")) {
				JugadorFreezee j = (JugadorFreezee) game.getJugador(event
						.getWhoClicked().getName());
				if (j == null) {
					return;
				}
				if(((PartidaFreezee) j.getPartida()).setTipo(j, equip)){
				pl.sendMessage(Lang.get("selected_team").replace("%Team%",
						equip.name()));
				}else{
					pl.sendMessage(Lang.get("setterEquipo.noFreezee"));
				}
			} else {
				pl.sendMessage(Lang.get("no_perms"));
			}
		}

		@Override
		public void execute(PlayerInteractEvent event) {

		}

	}

}
