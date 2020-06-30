package cl.bgmp.mobcointrophies.trophies;

import org.bukkit.scheduler.BukkitRunnable;

public class ProductionTask extends BukkitRunnable {
  private MobTrophy mobTrophy;

  public ProductionTask(MobTrophy mobTrophy) {
    this.mobTrophy = mobTrophy;
  }

  @Override
  public void run() {
    mobTrophy.setCoins(mobTrophy.getCoins() + mobTrophy.getTier().getProduction());
  }
}
