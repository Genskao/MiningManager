package fr.tropweb.miningmanager;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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

    /**
     * Get if the player have the permission.
     *
     * @param player     player bukkit object
     * @param permission enum of permission
     * @return true if the player have the permission or if he is OP or return false
     */
    public static boolean hasPerm(final Player player, final CommandManager permission) {
        // if player
        if (player == null) return false;

        // Ops can do everything
        if (player.isOp()) return true;

        // Check permission
        return player.hasPermission(permission.getPermission());
    }

    /**
     * Get file content from InputStream.
     *
     * @param inputStream resource that you want to get
     * @return
     * @see <a href="https://mkyong.com/java/how-to-convert-inputstream-to-string-in-java/">Perfect example here</a>.
     */
    public static String getFileContent(final InputStream inputStream) {

        // create byte buffer for the result
        final ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {

            // create buffer for the read
            final byte[] buffer = new byte[1024];

            // read
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            // return data
            return result.toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // empty return
        return result.toString();
    }
}
