package me.khmdev.Freezee.Game;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import me.khmdev.APIAuxiliar.Effects.ListenerFreeze;
import me.khmdev.APIAuxiliar.Effects.ParticleEffect;
import me.khmdev.APIAuxiliar.Effects.SendParticles;
import me.khmdev.APIBase.API;
import me.khmdev.APIGames.Auxiliar.IJugador;
import me.khmdev.APIGames.Auxiliar.Jugador;
import me.khmdev.APIGames.Auxiliar.ListenerGames;
import me.khmdev.APIGames.Auxiliar.ConstantesGames.Estado;
import me.khmdev.APIGames.Games.IGame;
import me.khmdev.APIGames.Partidas.PartidaTCT;
import me.khmdev.Freezee.Game.PartidaFreezee.TipoJugador;

public class ListenerFreezee extends ListenerGames {

	public ListenerFreezee(IGame plug) {
		super(plug);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {

		Jugador j = game.getJugador(event.getPlayer().getName());
		if (j == null 
				|| j.getPartida().getEstado() != Estado.EnCurso) {
			for (IJugador jj : game.getJugadores()) {
				if (jj.getPartida().getEstado() == Estado.EnCurso) {
					System.out.println(jj+" eliminar");
					event.getRecipients().remove(jj);
				}
			}
			return;
		} else {
			event.setCancelled(true);
			if (event.getMessage().startsWith("all")) {
				j.getPartida().sendAsToAll(j,
						event.getMessage().replaceFirst("all ", ""));
			} else {
				j.getPartida().sendAsToTeam(j, event.getMessage());
			}
		}
	}



	@EventHandler
	public void Asegurar(EntityExplodeEvent event) {
		MetadataValue meta = API.getMetadata(event.getEntity(), "PlayerFreeze");

		if (meta != null) {
			Entity ent = event.getEntity();
			event.setCancelled(true);
			Location loc = ent.getLocation();
			for (int i = 0; i < 4; i++) {
				SendParticles.send(ParticleEffect.LARGE_EXPLODE,
						loc.add(0, -4, 0), 1, 5, new Vector(0, 0, 0));
			}
			@SuppressWarnings("deprecation")
			Player attacker = Bukkit.getServer().getPlayer(meta.asString());
			if (attacker == null) {
				return;
			}
			Iterator<Entity> it = ent.getNearbyEntities(2, 2, 2).iterator();
			while (it.hasNext()) {

				Entity next = it.next();
				if (next instanceof Player) {
					int d=(int) (10-next.getLocation().distance(ent.getLocation()));
					d=d<0?0:d;
					System.out.println(d);
					((Player) next).damage(d*5
							, attacker.getPlayer());
				}
			}

		}
	}

	@EventHandler
	public void Asegurar(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {
			Player pl = (Player) event.getEntity();
			JugadorFreezee j = (JugadorFreezee) game.getJugador(pl.getName());
			if (j == null) {
				return;
			}
			if(ListenerFreeze.conteinPlayer(pl.getName())){
				event.setCancelled(true);
				return;
			}
			if (j.getTipo() == TipoJugador.Congelado) {
				event.setCancelled(true);
				return;
			}
			Entity killer = event.getDamager();
			if (killer instanceof Arrow) {
				Arrow a = (Arrow) killer;
				killer = (Entity) a.getShooter();
			}

			if (killer == null || !(killer instanceof Player)) {
				if(j.getTipo()==TipoJugador.Congelador){
					double health=pl.getHealth()-event.getDamage();
					health=health<0?0:health;
					pl.setHealth(health);
					event.setDamage(0);
				}
				System.out.println(event.getDamage());

				// event.setCancelled(true);
				return;

			}
			
			
			Player dam = (Player) killer;
			JugadorFreezee damJ = (JugadorFreezee) j.getPartida().getJugador(
					dam.getName());
			if(damJ==null) {
				if(j.getTipo()==TipoJugador.Congelador){
					double health=pl.getHealth()-event.getDamage();
					health=health<0?0:health;
					pl.setHealth(health);
					event.setDamage(0);
				}
				System.out.println(event.getDamage());

				// event.setCancelled(true);
				return;

			}
			if(ListenerFreeze.conteinPlayer(dam.getName())){
				event.setCancelled(true);
				return;
			}
			if (j.getTipo() == TipoJugador.Congelado) {
				event.setCancelled(true);
				return;
			}
			if (damJ.getTipo() == j.getTipo()) {
				event.setCancelled(true);
				return;
			}
			if(j.getTipo()==TipoJugador.Congelador){
				double health=pl.getHealth()-event.getDamage();
				health=health<0?0:health;
				pl.setHealth(health);
				event.setDamage(0);
			}
			System.out.println(event.getDamage());

		}
	}

	@Override
	protected void spawn(Jugador j, PlayerRespawnEvent event) {
		event.setRespawnLocation(((PartidaTCT) j.getPartida()).spawnZone(j));
		j.getPartida().Equipar(j);
		if(((JugadorFreezee) j).getTipo()==TipoJugador.Normal){
			((PartidaFreezee) j.getPartida()).spawnCongelado(j);
		}else if(((JugadorFreezee) j).getTipo()==TipoJugador.Congelado){
			((PartidaFreezee) j.getPartida()).spawnCongelado(j,-1);
		}
	}
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if(game.containsJugador(event.getWhoClicked().getName())){
			event.setCancelled(true);
		}
	}
	@Override
	protected void death(Jugador j, EntityDeathEvent event) {

	}

}
