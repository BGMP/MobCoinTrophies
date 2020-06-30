package cl.bgmp.mobcointrophies.gui;

import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.trophies.MobTrophy;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TrophyGUI extends GUI implements Listener {
  private MobTrophy mobTrophy;
  private HashMap<ItemStack, Integer> trophyGUI;

  public TrophyGUI(MobTrophy mobTrophy) {
    super(mobTrophy.getTier().getTitle(), 27);
    GUIComponents components = MobCoinTrophies.get().getGUIComponents();
    MobCoinTrophies.get().registerEvents(this);

    this.mobTrophy = mobTrophy;
    this.trophyGUI = components.builtTrophyGUI(mobTrophy);
  }

  @Override
  public void addContent() {
    super.addContent();
    for (ItemStack item : trophyGUI.keySet()) {
      setItem(trophyGUI.get(item), item);
    }
  }

  @Override
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!event.getInventory().equals(getInventory())) return;
    event.setCancelled(true);

    Inventory clickedInventory = event.getClickedInventory();
    if (clickedInventory == null || !clickedInventory.equals(getInventory())) return;

    Player player = (Player) event.getWhoClicked();
    int slot = event.getSlot();
    for (ItemStack item : trophyGUI.keySet()) {
      if (trophyGUI.get(item) != slot) continue;

      switch (slot) {
        case 13:
          mobTrophy.collectCoins(player, false);
          player.closeInventory();
          return;
        case 15:
          mobTrophy.pickUp(player);
          player.closeInventory();
          return;
        case 18:
          player.closeInventory();
          return;
        case 11:
        default:
          break;
      }
    }
  }
}
