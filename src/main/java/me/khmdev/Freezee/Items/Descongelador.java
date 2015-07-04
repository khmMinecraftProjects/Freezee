package me.khmdev.Freezee.Items;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.Freezee.Game.JugadorFreezee;
import me.khmdev.Freezee.Game.PartidaFreezee;
import me.khmdev.Freezee.Game.PartidaFreezee.TipoJugador;
import me.khmdev.Freezee.lang.Lang;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Descongelador extends CustomItem {
	PartidaFreezee partida;

	public Descongelador(PartidaFreezee p) {
		super(AuxPlayer.getItem(Material.FIRE, Lang.get("Descongelador.name") ));
		partida = p;
	}

	@Override
	public void execute(InventoryClickEvent event) {

	}

	@Override
	public void execute(PlayerInteractEvent event) {

	}

	@Override
	public void execute(PlayerInteractEntityEvent event) {
		if (ListenerFreeze.conteinPlayer(event.getPlayer().getName())) {
			return;
		}
		JugadorFreezee atack = (JugadorFreezee) partida.getJugador(event
				.getPlayer().getName());
		if (atack == null || atack.getTipo() != TipoJugador.Normal) {
			event.getPlayer().setItemInHand(null);
			return;
		}

		if (event.getRightClicked() instanceof Player) {
			Player atacado = (Player) event.getRightClicked();
			if (partida.JugadorEsta(atacado.getName())) {
				partida.desCongela(event.getPlayer(), atacado);
			}

		}
	}
}