package me.khmdev.Freezee;



import me.khmdev.Freezee.lang.Lang;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin{
	public static base base;
	public void onEnable() {
		Lang.init(this);
		if (!hasPluging("APIGames")) {
			getLogger().severe(
					getName()
							+ " se desactivo debido ha que no encontro la API");
			setEnabled(false);
			return;
		}
		
		base=new base(this);
	}
	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}

}
