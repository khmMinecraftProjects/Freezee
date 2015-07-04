package me.khmdev.Freezee.Items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIBase.Auxiliar.runKill;
import me.khmdev.Freezee.lang.Lang;
import me.khmdev.Freezee.base;

public class Azada extends CustomItem {
	private long timeout = 10000;

	public Azada() {
		super(AuxPlayer.getItem(Material.IRON_HOE, Lang.get("AzadaY.use")));
	}

	@Override
	public void execute(InventoryClickEvent event) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(final PlayerInteractEvent event) {
		if (ListenerFreeze.conteinPlayer(event.getPlayer().getName())) {
			return;
		}
		if (event.getItem().getDurability() != 0) {
			event.getPlayer().sendMessage(Lang.get("AzadaTNT.reload"));
			return;
		}

		Location l = event.getPlayer().getTargetBlock(null, 50).getLocation();
		l.add(0, 5, 0);
		final FallingBlock anv = l.getWorld().spawnFallingBlock(l,
				Material.ANVIL, (byte) 0);
		anv.setDropItem(false);

		runKill run = new runKill() {
			private int i = 0, max = 3, corazones = 4;

			@Override
			public void run() {
				System.out.println(anv.getVelocity());

				if (anv.getVelocity().equals(new Vector(0, 0, 0))) {
					if (i == 0) {
						for (Entity ent : anv.getNearbyEntities(1, 1, 1)) {
							if (ent instanceof Player) {
								((Player) ent).damage(corazones * 2,
										event.getPlayer());
							}

						}
					}
					if (i < max) {
						i++;
						return;
					}

					anv.remove();
					anv.getLocation().getBlock().setType(Material.AIR);
					kill();
				}
			}
		};
		int id = Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(base.getInstance(), run, 3, 3);
		run.setId(id);

		ItemStack out = event.getPlayer().getItemInHand();
		CItems.addTimer(out, timeout, event.getPlayer());
	}

}
