package cl.bgmp.mobcointrophies.listeners;

import cl.bgmp.mobcointrophies.Config;
import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.trophies.MobTrophy;
import cl.bgmp.mobcointrophies.trophies.TrophyManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChange implements Listener {

  @EventHandler
  public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
    World worldTo = event.getPlayer().getWorld();
    TrophyManager trophyManager = MobCoinTrophies.get().getTrophyManager();
    if (Config.SkyBlock.isEnabled()) {
      if (worldTo.equals(Bukkit.getWorld(Config.SkyBlock.getWorldName())))
        trophyManager.getPlayerTrophies(event.getPlayer()).forEach(MobTrophy::resumeProduction);
      else trophyManager.getPlayerTrophies(event.getPlayer()).forEach(MobTrophy::stopProduction);
    }
  }
}
