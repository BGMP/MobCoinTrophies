package cl.bgmp.mobcointrophies.GUI;

import cl.bgmp.mobcointrophies.Trophies.MobTrophy;
import cl.bgmp.mobcointrophies.Utils;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GUIComponents {
  ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
  ItemStack exitArrow = Utils.titledItemStack(Material.ARROW, "&cExit");

  private ItemStack pickupBarrier = Utils.titledItemStack(Material.BARRIER, "&cPickup Trophy");

  HashMap<ItemStack, Integer> builtTrophyGUI(MobTrophy mobTrophy) {
    ItemStack informationPaper =
        Utils.titledItemStackWithLore(
            Material.PAPER,
            "&9Information",
            new String[] {
              "&7Island Level Gain: &a" + mobTrophy.getTier().getLevelGain(),
              "&7Generation Rate: &a" + mobTrophy.getTier().getProduction() + "&7/min",
              "&7Limit per Island: &a" + mobTrophy.getTier().getLimit()
            });

    ItemStack collectDiamond =
        Utils.titledItemStack(
            Material.DIAMOND, "&bCollect &a" + mobTrophy.getCoins() + " &bMob Coins");

    return new HashMap<ItemStack, Integer>() {
      {
        put(informationPaper, 11);
        put(collectDiamond, 13);
        put(pickupBarrier, 15);
        put(exitArrow, 18);
      }
    };
  }
}
