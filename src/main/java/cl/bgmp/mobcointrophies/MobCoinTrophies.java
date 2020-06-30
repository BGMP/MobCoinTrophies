package cl.bgmp.mobcointrophies;

import cl.bgmp.mobcointrophies.commands.TrophyCommand;
import cl.bgmp.mobcointrophies.gui.GUIComponents;
import cl.bgmp.mobcointrophies.listeners.PlayerConnection;
import cl.bgmp.mobcointrophies.listeners.PlayerInteract;
import cl.bgmp.mobcointrophies.listeners.PlayerWorldChange;
import cl.bgmp.mobcointrophies.trophies.TrophyManager;
import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import com.sk89q.minecraft.util.commands.CommandsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class MobCoinTrophies extends JavaPlugin {
  private static MobCoinTrophies mobCoinTrophies;
  private TrophyManager trophyManager;
  private GUIComponents guiComponents;

  private CommandsManager commands;
  private CommandsManagerRegistration commandRegistry;

  public static MobCoinTrophies get() {
    return mobCoinTrophies;
  }

  public TrophyManager getTrophyManager() {
    return trophyManager;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      Command command,
      @NotNull String label,
      @NotNull String[] args) {
    try {
      this.commands.execute(command.getName(), args, sender, sender);
    } catch (CommandPermissionsException exception) {
      sender.sendMessage(ChatConstant.NO_PERMISSION.formatAsException());
    } catch (MissingNestedCommandException exception) {
      sender.sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + exception.getUsage());
    } catch (CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
      sender.sendMessage(ChatColor.RED + exception.getUsage());
    } catch (WrappedCommandException exception) {
      if (exception.getCause() instanceof NumberFormatException) {
        sender.sendMessage(ChatConstant.NUMBER_STRING_EXCEPTION.formatAsException());
      } else {
        sender.sendMessage(ChatConstant.UNKNOWN_ERROR.formatAsException());
        exception.printStackTrace();
      }
    } catch (CommandException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }
    return true;
  }

  public GUIComponents getGUIComponents() {
    return guiComponents;
  }

  @Override
  public void onEnable() {
    mobCoinTrophies = this;
    loadConfiguration();

    commands = new BukkitCommandsManager();
    commandRegistry = new CommandsManagerRegistration(this, commands);

    trophyManager = new TrophyManager();

    new BukkitRunnable() {
      @Override
      public void run() {
        trophyManager.loadRegistrars();
        this.cancel();
      }
    }.runTaskLater(this, 5L);

    guiComponents = new GUIComponents();

    registerCommands();
    registerEvents(new PlayerInteract(), new PlayerConnection(), new PlayerWorldChange());
  }

  @Override
  public void onDisable() {
    trophyManager.saveAll();
    trophyManager.removeArmorStands();
  }

  private void registerCommands() {
    commandRegistry.register(TrophyCommand.TrophyParentCommand.class);
    commandRegistry.register(TrophyCommand.class);
  }

  public void registerEvents(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

  private void loadConfiguration() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
