package cl.bgmp.mobcointrophies.Trophies;

import cl.bgmp.mobcointrophies.Config;
import cl.bgmp.mobcointrophies.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Tier {
  TIER_1(
      Config.Tier1.getId(),
      Config.Tier1.getTitle(),
      Config.Tier1.getProduction(),
      Config.Tier1.getLevelGain(),
      Config.Tier1.getLimit(),
      Utils.titledItemStackWithLore(
          Material.ARMOR_STAND,
          Config.Tier1.getTitle(),
          new String[] {
            "&7Production: &a3&7/min",
            "&7Level Gain:&a " + Config.Tier1.getLevelGain(),
            "&7Limit:&a " + Config.Tier1.getLimit()
          })),
  TIER_2(
      Config.Tier2.getId(),
      Config.Tier2.getTitle(),
      Config.Tier2.getProduction(),
      Config.Tier2.getLevelGain(),
      Config.Tier2.getLimit(),
      Utils.titledItemStackWithLore(
          Material.ARMOR_STAND,
          Config.Tier2.getTitle(),
          new String[] {
            "&7Production: &a6&7/min",
            "&7Level Gain:&a " + Config.Tier2.getLevelGain(),
            "&7Limit:&a " + Config.Tier2.getLimit()
          })),
  TIER_3(
      Config.Tier3.getId(),
      Config.Tier3.getTitle(),
      Config.Tier3.getProduction(),
      Config.Tier3.getLevelGain(),
      Config.Tier3.getLimit(),
      Utils.titledItemStackWithLore(
          Material.ARMOR_STAND,
          Config.Tier3.getTitle(),
          new String[] {
            "&7Production: &a12&7/min",
            "&7Level Gain:&a " + Config.Tier3.getLevelGain(),
            "&7Limit:&a " + Config.Tier3.getLimit()
          })),
  BOSS(
      Config.Boss.getId(),
      Config.Boss.getTitle(),
      Config.Boss.getProduction(),
      Config.Boss.getLevelGain(),
      Config.Boss.getLimit(),
      Utils.titledItemStackWithLore(
          Material.ARMOR_STAND,
          Config.Boss.getTitle(),
          new String[] {
            "&7Production: &a10&7/min",
            "&7Level Gain:&a " + Config.Boss.getLevelGain(),
            "&7Limit:&a " + Config.Boss.getLimit()
          })),
  KOTH(
      Config.Koth.getId(),
      Config.Koth.getTitle(),
      Config.Koth.getProduction(),
      Config.Koth.getLevelGain(),
      Config.Koth.getLimit(),
      Utils.titledItemStackWithLore(
          Material.ARMOR_STAND,
          Config.Koth.getTitle(),
          new String[] {
            "&7Production: &a20&7/min",
            "&7Level Gain:&a " + Config.Koth.getLevelGain(),
            "&7Limit:&a " + Config.Koth.getLimit()
          }));

  private String id;
  private String title;
  private int production;
  private int levelGain;
  private int limit;
  private ItemStack itemStack;

  Tier(String id, String title, int production, int levelGain, int limit, ItemStack itemStack) {
    this.id = id;
    this.title = title;
    this.production = production;
    this.levelGain = levelGain;
    this.limit = limit;
    this.itemStack = itemStack;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return Utils.colourify(title + "&a");
  }

  public int getProduction() {
    return production;
  }

  public int getLevelGain() {
    return levelGain;
  }

  public int getLimit() {
    return limit;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }
}
