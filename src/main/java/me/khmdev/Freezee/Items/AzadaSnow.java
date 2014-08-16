package me.khmdev.Freezee.Items;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;

public class AzadaSnow extends CustomItem {
	public AzadaSnow() {
		super(AuxPlayer.getItem(Material.DIAMOND_HOE, "Lanza bolas"));
	}

	@Override
	public void execute(InventoryClickEvent event) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(PlayerInteractEvent event) {
		if(ListenerFreeze.conteinPlayer(event.getPlayer().getName())){return;}
		event.getPlayer().launchProjectile(Snowball.class);
		ItemStack it = event.getPlayer().getItemInHand();
		double n = VentajaSnow.use(event.getPlayer().getName());

		if (n <= 0) {
			event.getPlayer().setItemInHand(null);
		} else {
			it.setDurability((short) (it.getType().getMaxDurability() * (1 - (n / VentajaSnow
					.getMax()))));
		}
		event.getPlayer().updateInventory();
	}

	public boolean isthis(ItemStack it) {
		//ItemStack ite = it.clone();
		//ite.setDurability(ite.getType().getMaxDurability());
		short s=it.getDurability();
		it.setDurability(item.getDurability());
		boolean b=item.isSimilar(it);
		it.setDurability(s);
		return b;
	}
}
