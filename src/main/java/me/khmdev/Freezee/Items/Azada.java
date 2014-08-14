package me.khmdev.Freezee.Items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;

public class Azada extends CustomItem{
	private long timeout=20000;

	public Azada(){
		super(AuxPlayer.getItem(Material.WOOD_HOE, "Lanza yunques"));
	}
	@Override
	public void execute(InventoryClickEvent event) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(PlayerInteractEvent event) {
		if(event.getItem().getDurability()!=0){
			event.getPlayer().sendMessage("Aun no se ha recargado el item");
			return;
		}
		
		Location l=event.getPlayer().getTargetBlock(null,50).getLocation();
		l.add(0, 5, 0);
		l.getWorld().spawnFallingBlock(l, Material.ANVIL, (byte) 0);
		
		ItemStack out=event.getPlayer().getItemInHand();
		CItems.addTimer(out, timeout,event.getPlayer());
	}



}
