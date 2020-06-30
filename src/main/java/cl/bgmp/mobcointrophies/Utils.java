package cl.bgmp.mobcointrophies;

import cl.bgmp.mobcointrophies.trophies.Tier;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
  public static String colourify(String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }

  public static ItemStack titledItemStack(Material material, String title) {
    ItemStack itemStack = new ItemStack(material);
    ItemMeta itemMeta = itemStack.getItemMeta();

    itemMeta.setDisplayName(colourify(title));
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  public static ItemStack titledItemStackWithLore(Material material, String title, String[] lore) {
    ItemStack itemStack = titledItemStack(material, title);
    ItemMeta itemMeta = itemStack.getItemMeta();

    int i = 0;
    for (String line : lore) {
      lore[i] = colourify(line);
      i++;
    }

    itemMeta.setLore(Arrays.asList(lore));
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  public static void ensureItemObtainment(Player player, ItemStack itemStack) {
    if (player.getInventory().firstEmpty() == -1)
      player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
    else player.getInventory().addItem(itemStack);
  }

  public static float mirrorYaw(float yaw) {
    yaw = Math.abs(yaw);
    if (yawIsInRange(yaw, 45f, 135f)) return 90f;
    if (yawIsInRange(yaw, 135f, 225f)) return 0f;
    if (yawIsInRange(yaw, 225f, 315f)) return -90f;
    else return 180f;
  }

  private static boolean yawIsInRange(float yaw, float min, float max) {
    return yaw >= min && yaw <= max;
  }

  public static void modifyArmorStand(ArmorStand armorStand, Tier tier) {
    armorStand.setInvulnerable(true);
    armorStand.setDisabledSlots(EquipmentSlot.values());
    armorStand.setCustomName(tier.getTitle());
    armorStand.setCustomNameVisible(true);
    armorStand.setAI(false);
    armorStand.setGravity(false);
    armorStand.setCanMove(false);

    EntityEquipment equipment = armorStand.getEquipment();

    assert equipment != null;
    switch (tier) {
      case TIER_1:
        equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
        equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
        break;
      case TIER_2:
        equipment.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
        equipment.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.GOLDEN_BOOTS));
        break;
      case TIER_3:
        equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        break;
      case BOSS:
        equipment.setHelmet(new ItemStack(Material.LEATHER_HELMET));
        equipment.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        break;
      case KOTH:
        equipment.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        equipment.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        break;
    }

    equipment.setItemInMainHand(new ItemStack(Material.SUNFLOWER));
  }

  public static String formatLocation(Location location) {
    return location.getWorld().getName()
        + ","
        + location.getX()
        + ","
        + location.getY()
        + ","
        + location.getZ()
        + ","
        + location.getYaw()
        + ","
        + location.getPitch();
  }

  /**
   * {@link this#formatLocation(Location)} split[0] => World Name, split[1] => X coordinate,
   * split[2] => Y coordinate, split[3] => Z coordinate, split[4] => Yaw, split[5] => Pitch
   *
   * @param split Array generated from the config location format, separated by commas
   * @return The resulting location object, built with the passed indexes
   */
  public static Location buildLocation(String[] split) {
    try {
      return new Location(
          Bukkit.getWorld(split[0]),
          Double.parseDouble(split[1]),
          Double.parseDouble(split[2]),
          Double.parseDouble(split[3]),
          Float.parseFloat(split[4]),
          Float.parseFloat(split[5]));
    } catch (NullPointerException | NumberFormatException e) {
      Bukkit.getLogger().severe(ChatConstant.CORRUPTED_LOCATION.formatAsException());
      e.printStackTrace();
    }
    return null;
  }
}
