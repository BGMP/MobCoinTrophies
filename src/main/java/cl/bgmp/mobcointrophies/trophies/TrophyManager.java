package cl.bgmp.mobcointrophies.trophies;

import cl.bgmp.mobcointrophies.Config;
import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class TrophyManager {
  private ConfigurationSection configurationSection =
      MobCoinTrophies.get().getConfig().getConfigurationSection("registrar");
  private Set<MobTrophy> trophyRegistrar = new HashSet<>();
  private HashMap<UUID, ArmorStand> interactionRegistrar = new HashMap<>();

  public TrophyManager() {}

  public Set<MobTrophy> getTrophyRegistrar() {
    return trophyRegistrar;
  }

  public HashMap<UUID, ArmorStand> getInteractionRegistrar() {
    return interactionRegistrar;
  }

  public void loadRegistrars() {
    configurationSection
        .getKeys(false)
        .forEach(
            key -> {
              if (key.equals("format")) return;
              String ownerString = configurationSection.getString(key + ".owner");
              String locationString = configurationSection.getString(key + ".location");
              String tier = configurationSection.getString(key + ".tier");

              Tier tierMatch = null;
              for (Tier tierInstance : Tier.values()) {
                if (tierInstance.getId().equals(tier)) tierMatch = tierInstance;
              }

              if (ownerString == null || locationString == null || tierMatch == null) return;

              UUID id = UUID.fromString(key);
              UUID trophyOwnerId = UUID.fromString(ownerString);
              Location location = Utils.buildLocation(locationString.split(","));
              int coins = configurationSection.getInt(key + ".coins");

              assert location != null;
              loadTrophy(new MobTrophy(id, trophyOwnerId, tierMatch, location, coins));
            });
  }

  public void stopProductionForPlayer(Player player) {
    trophyRegistrar.forEach(
        mobTrophy -> {
          if (mobTrophy.getOwner().equals(player.getUniqueId())) {
            mobTrophy.stopProduction();
          }
        });
  }

  public void resumeProductionForPlayer(Player player) {
    if (!player.getWorld().equals(Bukkit.getWorld(Config.SkyBlock.getWorldName()))) return;
    trophyRegistrar.forEach(
        mobTrophy -> {
          if (mobTrophy.getOwner().equals(player.getUniqueId())) {
            mobTrophy.resumeProduction();
          }
        });
  }

  public void registerTrophy(MobTrophy mobTrophy) {
    String key = mobTrophy.getId().toString();

    configurationSection.set(key + ".owner", mobTrophy.getOwner().toString());
    configurationSection.set(key + ".tier", mobTrophy.getTier().getId());
    configurationSection.set(key + ".coins", mobTrophy.getCoins());
    configurationSection.set(key + ".location", Utils.formatLocation(mobTrophy.getLocation()));
    MobCoinTrophies.get().saveConfig();
    loadTrophy(mobTrophy);
  }

  public void destroyTrophy(MobTrophy mobTrophy) {
    configurationSection.set(mobTrophy.getId().toString(), null);
    MobCoinTrophies.get().saveConfig();
    unloadTrophy(mobTrophy);
  }

  private void loadTrophy(MobTrophy mobTrophy) {
    trophyRegistrar.add(mobTrophy);
  }

  private void unloadTrophy(MobTrophy mobTrophy) {
    trophyRegistrar.remove(mobTrophy);
  }

  public Set<MobTrophy> getPlayerTrophies(Player player) {
    Set<MobTrophy> mobTrophies = new HashSet<>();
    for (MobTrophy mobTrophy : trophyRegistrar) {
      if (!mobTrophy.getOwner().equals(player.getUniqueId())) continue;
      mobTrophies.add(mobTrophy);
    }
    return mobTrophies;
  }

  public void saveCoins(MobTrophy mobTrophy) {
    if (mobTrophy.exists()) {
      configurationSection.set(mobTrophy.getId().toString() + ".coins", mobTrophy.getCoins());
      MobCoinTrophies.get().saveConfig();
    } else configurationSection.set(mobTrophy.getId().toString(), null);
  }

  public void saveAll() {
    trophyRegistrar.forEach(MobTrophy::saveCoins);
  }

  public void removeArmorStands() {
    trophyRegistrar.forEach(MobTrophy::removeArmorStand);
  }

  public Optional<MobTrophy> getTrophyByLocation(Location location) {
    return trophyRegistrar.stream()
        .filter(mobTrophy -> mobTrophy.getLocation().equals(location))
        .findFirst();
  }
}
