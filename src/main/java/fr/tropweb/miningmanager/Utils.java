package fr.tropweb.miningmanager;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static void green(Player player, String message, Object... args) {
        player.sendMessage(String.format(ChatColor.GREEN + message, args));
    }

    public static void red(Player player, String message, Object... args) {
        player.sendMessage(String.format(ChatColor.RED + message, args));
    }

    public static boolean hasPerm(Player player, SubCommand subCommand) {
        return hasPerm(player, subCommand.permission());
    }

    public static boolean hasPerm(Player player, CommandManager permission) {
        // if player
        if (player == null) return false;

        // Ops can do everything
        if (player.isOp()) return true;

        // Check permission
        return player.hasPermission(permission.getPermission());
    }
}
