package cl.bgmp.mobcointrophies;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
  private static Configuration getConfiguration() {
    MobCoinTrophies mobCoinTrophies = MobCoinTrophies.get();
    if (mobCoinTrophies != null) {
      return mobCoinTrophies.getConfig();
    } else {
      return new YamlConfiguration();
    }
  }

  public static class Tier1 {
    public static String getId() {
      return getConfiguration().getString("trophies.tier1.id");
    }

    public static String getTitle() {
      return getConfiguration().getString("trophies.tier1.title");
    }

    public static int getProduction() {
      return getConfiguration().getInt("trophies.tier1.production");
    }

    public static int getLevelGain() {
      return getConfiguration().getInt("trophies.tier1.levelGain");
    }

    public static int getLimit() {
      return getConfiguration().getInt("trophies.tier1.limit");
    }
  }

  public static class Tier2 {
    public static String getId() {
      return getConfiguration().getString("trophies.tier2.id");
    }

    public static String getTitle() {
      return getConfiguration().getString("trophies.tier2.title");
    }

    public static int getProduction() {
      return getConfiguration().getInt("trophies.tier2.production");
    }

    public static int getLevelGain() {
      return getConfiguration().getInt("trophies.tier2.levelGain");
    }

    public static int getLimit() {
      return getConfiguration().getInt("trophies.tier2.limit");
    }
  }

  public static class Tier3 {
    public static String getId() {
      return getConfiguration().getString("trophies.tier3.id");
    }

    public static String getTitle() {
      return getConfiguration().getString("trophies.tier3.title");
    }

    public static int getProduction() {
      return getConfiguration().getInt("trophies.tier3.production");
    }

    public static int getLevelGain() {
      return getConfiguration().getInt("trophies.tier3.levelGain");
    }

    public static int getLimit() {
      return getConfiguration().getInt("trophies.tier3.limit");
    }
  }

  public static class Boss {
    public static String getId() {
      return getConfiguration().getString("trophies.boss.id");
    }

    public static String getTitle() {
      return getConfiguration().getString("trophies.boss.title");
    }

    public static int getProduction() {
      return getConfiguration().getInt("trophies.boss.production");
    }

    public static int getLevelGain() {
      return getConfiguration().getInt("trophies.boss.levelGain");
    }

    public static int getLimit() {
      return getConfiguration().getInt("trophies.boss.limit");
    }
  }

  public static class Koth {
    public static String getId() {
      return getConfiguration().getString("trophies.koth.id");
    }

    public static String getTitle() {
      return getConfiguration().getString("trophies.koth.title");
    }

    public static int getProduction() {
      return getConfiguration().getInt("trophies.koth.production");
    }

    public static int getLevelGain() {
      return getConfiguration().getInt("trophies.koth.levelGain");
    }

    public static int getLimit() {
      return getConfiguration().getInt("trophies.koth.limit");
    }
  }

  public static class SkyBlock {
    public static boolean isEnabled() {
      return getConfiguration().getBoolean("options.skyblock.enabled");
    }

    public static String getWorldName() {
      return getConfiguration().getString("options.skyblock.world");
    }
  }
}
