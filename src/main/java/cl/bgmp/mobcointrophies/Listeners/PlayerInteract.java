package cl.bgmp.mobcointrophies.Listeners;

import cl.bgmp.mobcointrophies.ChatConstant;
import cl.bgmp.mobcointrophies.GUI.TrophyGUI;
import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.Trophies.MobTrophy;
import cl.bgmp.mobcointrophies.Trophies.Tier;
import cl.bgmp.mobcointrophies.Utils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
    EntityType entityType = event.getRightClicked().getType();
    if (entityType != EntityType.ARMOR_STAND) return;

    Player player = event.getPlayer();
    ArmorStand armorStand = (ArmorStand) event.getRightClicked();
    MobCoinTrophies.get()
        .getTrophyManager()
        .getInteractionRegistrar()
        .put(player.getUniqueId(), armorStand);

    Optional<MobTrophy> clickedTrophy =
        MobCoinTrophies.get().getTrophyManager().getTrophyByLocation(armorStand.getLocation());
    if (!clickedTrophy.isPresent()) {
      return;
    }

    if (!clickedTrophy.get().getOwner().equals(player.getUniqueId())) {
      player.sendMessage(ChatConstant.CANT_USE_TROPHY.formatAsException());
      return;
    }

    event.getPlayer().openInventory(new TrophyGUI(clickedTrophy.get()).getInventory());
  }

  @SuppressWarnings("deprecation")
  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerInteract(PlayerInteractEvent event) {
    Block clickedBlock = event.getClickedBlock();
    if (clickedBlock == null) return;

    Action action = event.getAction();
    if (action != Action.RIGHT_CLICK_BLOCK) return;

    Player player = event.getPlayer();
    ItemStack mainHandItem = player.getInventory().getItemInMainHand();
    ItemStack offHandItem = player.getInventory().getItemInOffHand();
    ItemStack legacyHandItem = player.getInventory().getItemInHand();
    Material[] materialInstances =
        new Material[] {mainHandItem.getType(), offHandItem.getType(), legacyHandItem.getType()};
    if (Arrays.asList(materialInstances).contains(Material.AIR)
        && !Arrays.asList(materialInstances).contains(Material.ARMOR_STAND)) return;

    Set<List<String>> loreInstances =
        new HashSet<List<String>>() {
          {
            if (mainHandItem.hasItemMeta() && mainHandItem.getItemMeta().hasLore())
              add(mainHandItem.getItemMeta().getLore());
            if (offHandItem.hasItemMeta() && offHandItem.getItemMeta().hasLore())
              add(offHandItem.getItemMeta().getLore());
            if (legacyHandItem.hasItemMeta() && legacyHandItem.getItemMeta().hasLore())
              add(legacyHandItem.getItemMeta().getLore());
          }
        };

    Optional<Tier> tierMatch = Optional.empty();
    for (List<String> lore : loreInstances) {
      tierMatch =
          Arrays.stream(Tier.values())
              .filter(tier -> lore.equals(tier.getItemStack().getLore()))
              .findFirst();
      if (tierMatch.isPresent()) break;
    }

    if (!tierMatch.isPresent()) return;

    event.setCancelled(true);

    if (event.getBlockFace() != BlockFace.UP) {
      player.sendMessage(ChatConstant.MUST_BE_ON_GROUND.formatAsException());
      return;
    }

    if (mainHandItem.getType() == Material.ARMOR_STAND)
      mainHandItem.setAmount(mainHandItem.getAmount() - 1);
    else if (offHandItem.getType() == Material.ARMOR_STAND)
      offHandItem.setAmount(mainHandItem.getAmount() - 1);
    else if (legacyHandItem.getType() == Material.ARMOR_STAND)
      legacyHandItem.setAmount(mainHandItem.getAmount() - 1);

    Location fixedTrophyLocation = clickedBlock.getLocation().add(0.5, 1, 0.5);
    fixedTrophyLocation.setYaw(Utils.mirrorYaw(player.getLocation().getYaw()));

    ArmorStand armorStand =
        (ArmorStand) player.getWorld().spawnEntity(fixedTrophyLocation, EntityType.ARMOR_STAND);

    MobCoinTrophies.get()
        .getTrophyManager()
        .registerTrophy(
            new MobTrophy(
                armorStand,
                UUID.randomUUID(),
                player.getUniqueId(),
                tierMatch.get(),
                fixedTrophyLocation));
  }

  @EventHandler
  public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
    Entity entity = event.getEntity();
    if (entity.getType() != EntityType.ARMOR_STAND) return;

    Location location = entity.getLocation();
    final Optional<MobTrophy> damagedTrophy =
        MobCoinTrophies.get().getTrophyManager().getTrophyByLocation(location);
    if (damagedTrophy.isPresent()) event.setCancelled(true);
  }
}
