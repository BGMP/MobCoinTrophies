package cl.bgmp.mobcointrophies.listeners;

import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.trophies.TrophyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    TrophyManager trophyManager = MobCoinTrophies.get().getTrophyManager();
    trophyManager.resumeProductionForPlayer(event.getPlayer());
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent event) {
    TrophyManager trophyManager = MobCoinTrophies.get().getTrophyManager();
    trophyManager.stopProductionForPlayer(event.getPlayer());
  }
}
