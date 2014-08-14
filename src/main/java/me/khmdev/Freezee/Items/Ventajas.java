package me.khmdev.Freezee.Items;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIGames.Books.Ventajas.GestorDeVentajas;
import me.khmdev.APIGames.Books.Ventajas.VentajaInitItem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Ventajas {

	public static void init(GestorDeVentajas ventaja) {
		Azada it = new Azada();
		CItems.addItem(it);
		ventaja.addVentaja(new VentajaInitItem(it.getItem(), 10));
		AzadaTNT tnt = new AzadaTNT();
		CItems.addItem(tnt);
		ventaja.addVentaja(new VentajaInitItem(tnt.getItem(), 30));

		ventaja.addVentaja(new VentajaInitItem(
				new ItemStack(Material.WOOD_SWORD), 5));
		ventaja.addVentaja(new VentajaInitItem(
				new ItemStack(Material.STONE_SWORD), 10));
		ventaja.addVentaja(new VentajaInitItem(
				new ItemStack(Material.IRON_SWORD), 20));
		
		AzadaSnow sn = new AzadaSnow();
		CItems.addItem(sn);
		ventaja.addVentaja(new VentajaInitItem(sn.getItem(), 60));
	}
}
