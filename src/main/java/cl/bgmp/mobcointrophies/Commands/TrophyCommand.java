package cl.bgmp.mobcointrophies.Commands;

import cl.bgmp.mobcointrophies.ChatConstant;
import cl.bgmp.mobcointrophies.Trophies.Tier;
import cl.bgmp.mobcointrophies.Utils;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrophyCommand {
  @Command(
      aliases = {"add"},
      desc = "Gives a trophy to a player.",
      usage = "[player] [tier1 | tier2 | tier3 | boss | koth]",
      min = 2,
      max = 2)
  @CommandPermissions("trophies.give")
  public static void give(final CommandContext args, final CommandSender sender) {
    String receiver = args.getString(0);
    Player receiverPlayer = Bukkit.getPlayer(receiver);
    if (receiverPlayer == null) {
      sender.sendMessage(ChatConstant.INVALID_PLAYER.formatAsException());
      return;
    }

    String tierId = args.getString(1);
    Tier tierMatch =
        Arrays.stream(Tier.values())
            .filter(tier -> tier.getId().equalsIgnoreCase(tierId))
            .findFirst()
            .orElse(null);
    if (tierMatch == null) {
      sender.sendMessage(ChatConstant.INVALID_TROPHY.formatAsException());
      return;
    }

    Utils.ensureItemObtainment(receiverPlayer, tierMatch.getItemStack());
    receiverPlayer.sendMessage(
        ChatConstant.TROPHY_RECEIVED.formatAsSuccess().replace("{0}", tierMatch.getTitle()));
  }

  public static class TrophyParentCommand {
    @Command(
        aliases = {"trophy", "trophies"},
        desc = "Trophies's node command.")
    @NestedCommand(TrophyCommand.class)
    public static void trophies(final CommandContext args, final CommandSender sender) {}
  }
}
