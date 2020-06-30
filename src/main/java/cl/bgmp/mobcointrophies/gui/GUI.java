package cl.bgmp.mobcointrophies.gui;

import cl.bgmp.mobcointrophies.MobCoinTrophies;
import cl.bgmp.mobcointrophies.Utils;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

abstract class GUI {
  private Inventory inventory;
  private String title;

  @SuppressWarnings("deprecation")
  GUI(String title, int size) {
    this.title = title;
    this.inventory = Bukkit.createInventory(null, size, Utils.colourify(title));
    Bukkit.getScheduler()
        .scheduleAsyncRepeatingTask(MobCoinTrophies.get(), this::addContent, 0L, 5L);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public String getTitle() {
    return title;
  }

  public void addContent() {
    if (inventory.getViewers().isEmpty()) return;
    for (int i = 0; i < inventory.getSize(); i++) {
      if (inventory.getItem(i) == null
          || Objects.requireNonNull(inventory.getItem(i)).getType().equals(Material.AIR)) {
        setItem(i, MobCoinTrophies.get().getGUIComponents().black);
      }
    }
  }

  public void setItem(int slot, ItemStack item) {
    if (getInventory().getItem(slot) == null
        || !Objects.requireNonNull(getInventory().getItem(slot)).isSimilar(item)) {
      getInventory().setItem(slot, item);
    }
  }

  public abstract void onInventoryClick(InventoryClickEvent event);
}
