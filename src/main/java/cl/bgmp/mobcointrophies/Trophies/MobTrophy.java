package cl.bgmp.mobcointrophies.Trophies;

import cl.bgmp.mobcointrophies.ChatConstant;
import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.Utils;
import java.util.Optional;
import java.util.UUID;
import me.swanis.mobcoins.MobCoinsAPI;
import me.swanis.mobcoins.profile.Profile;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class MobTrophy {
  private ArmorStand armorStand;
  private UUID id;
  private UUID owner;
  private Tier tier;
  private Location location;
  private ProductionTask productionTask;
  private int coins = 0;

  public MobTrophy(ArmorStand armorStand, UUID id, UUID owner, Tier tier, Location location) {
    this.armorStand = armorStand;
    Utils.modifyArmorStand(armorStand, tier);
    this.id = id;
    this.owner = owner;
    this.tier = tier;
    this.location = location;
    this.productionTask = new ProductionTask(this);
    productionTask.runTaskTimerAsynchronously(MobCoinTrophies.get(), 1200L, 1200L);
  }

  public MobTrophy(UUID id, UUID owner, Tier tier, Location location, int coins) {
    this.armorStand =
        (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
    Utils.modifyArmorStand(armorStand, tier);
    this.id = id;
    this.owner = owner;
    this.tier = tier;
    this.location = location;
    this.coins = coins;
    this.productionTask = new ProductionTask(this);
    productionTask.runTaskTimerAsynchronously(MobCoinTrophies.get(), 1200L, 1200L);
  }

  public UUID getId() {
    return id;
  }

  public UUID getOwner() {
    return owner;
  }

  public Tier getTier() {
    return tier;
  }

  public Location getLocation() {
    return location;
  }

  public int getCoins() {
    return coins;
  }

  public void setCoins(int coins) {
    this.coins = coins;
  }

  public void stopProduction() {
    productionTask.cancel();
  }

  public void resumeProduction() {
    productionTask = new ProductionTask(this);
    productionTask.runTaskTimerAsynchronously(MobCoinTrophies.get(), 1200L, 1200L);
  }

  public void pickUp(Player picker) {
    Utils.ensureItemObtainment(picker, tier.getItemStack());
    collectCoins(picker, true);
    productionTask.cancel();
    armorStand.remove();
    MobCoinTrophies.get()
        .getTrophyManager()
        .getInteractionRegistrar()
        .get(picker.getUniqueId())
        .remove();
    MobCoinTrophies.get().getTrophyManager().destroyTrophy(this);
  }

  public void collectCoins(Player collector, boolean silent) {
    Profile mobcoinsProfile = MobCoinsAPI.getProfileManager().getProfile(collector.getUniqueId());
    final int collected = this.getAndCollectProducedCoins();

    if (collected == 0 && !silent) {
      collector.sendMessage(ChatConstant.ZERO_COINS.formatAsException());
      return;
    }

    mobcoinsProfile.setMobCoins(mobcoinsProfile.getMobCoins() + collected);
    collector.sendMessage(
        ChatConstant.COINS_REDEEMED
            .formatAsSuccess()
            .replace("{0}", Utils.colourify("&a" + collected)));
  }

  private int getAndCollectProducedCoins() {
    final int toCollect = coins;
    resetCoins();
    return toCollect;
  }

  public void resetCoins() {
    this.coins = 0;
  }

  public void saveCoins() {
    MobCoinTrophies.get().getTrophyManager().saveCoins(this);
  }

  public void removeArmorStand() {
    this.armorStand.remove();
  }

  public boolean exists() {
    final Optional<MobTrophy> trophyMatch =
        MobCoinTrophies.get().getTrophyManager().getTrophyRegistrar().stream()
            .filter(mobTrophy -> mobTrophy.equals(this))
            .findFirst();
    return trophyMatch.isPresent();
  }
}
