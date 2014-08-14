package me.khmdev.Freezee.Items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIBase.API;

public class AzadaTNT extends CustomItem{
	private long timeout=20000;
	public AzadaTNT(){
		super(AuxPlayer.getItem(Material.GOLD_HOE, "Lanza TNT"));
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
		
		TNTPrimed tnt=(TNTPrimed) l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
		tnt.setFuseTicks(3*4);
		API.setMetadata(tnt, "PlayerFreeze", event.getPlayer().getName());
		ItemStack out=event.getPlayer().getItemInHand();
		CItems.addTimer(out, timeout,event.getPlayer());
	}



}
