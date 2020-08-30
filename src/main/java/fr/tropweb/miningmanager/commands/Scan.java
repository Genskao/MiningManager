package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static fr.tropweb.miningmanager.commands.struct.CommandManager.AUTO_SCAN;
import static fr.tropweb.miningmanager.commands.struct.CommandManager.SCAN;

public class Scan implements SubCommand {
    private final Engine engine;

    public Scan(final Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {

        // get player data from memory
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // check if economy plugin is enabled and if there is price
        if (this.engine.getEconomyPlugin().isEnabled(this.engine.getSettings().getScanPrice())) {

            // check if player can pay and take money
            if (!this.engine.getEconomyPlugin().takeMoney(player, this.engine.getSettings().getScanPrice())) {

                // player should have enough money
                throw new CommandException("You don't have enough money to scan this chunk.");
            }

            // economy message about the success
            Utils.green(player, "You have spent %s$ to start scan.", this.engine.getSettings().getScanPrice());
        }

        // check if player want to stop auto scan
        if (attribute == AUTO_SCAN) {

            // set or unset the auto scan
            playerLite.setAutoScan(!playerLite.isAutoScan());

            // show result to the player
            if (playerLite.isAutoScan()) {
                this.scanChunkOfPlayer(this.engine, player, player.getLocation().getChunk());
                Utils.green(player, "the auto scan has been activated.");
            } else {
                Utils.green(player, "the auto scan has been unactivated.");
            }
        }

        // scan chunk from player
        else {
            this.scanChunkOfPlayer(this.engine, player, player.getLocation().getChunk());
        }
    }

    @Override
    public CommandManager help() {
        return SCAN;
    }

    @Override
    public CommandManager permission() {
        return SCAN;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList(AUTO_SCAN);
    }

    public static void scanChunkOfPlayer(final Engine engine, final Player player, final Chunk chunk) {

        // check list of precious block
        final int[] amount = engine.getChunkEngine().getMaterialAmount(chunk);

        // check the precious resources
        final EnumSet<Material> preciousOre = engine.getBlockEngine().getPreciousOre();

        // build the empty resources list
        final StringBuilder resources = new StringBuilder();

        // check all materials
        for (final Material ore : preciousOre) {
            final int count = amount[ore.ordinal()];
            if (count > 0) {
                resources.append(ChatColor.GREEN).append("  - ")
                        .append(ChatColor.YELLOW).append(ore.name())
                        .append(ChatColor.GREEN).append(": ")
                        .append(count)
                        .append("\n");
            }
        }

        // build empty message
        final StringBuilder message = new StringBuilder();

        // check if there is precious resource
        if (resources.length() > 0) {
            message.append(String.format(ChatColor.BLUE + "You have found these precious resources (chunk: %s, %s):\n", chunk.getX(), chunk.getZ()));
            message.append(resources);
        }

        // inform the player about the empty chuck
        else {
            message.append(String.format(ChatColor.GRAY + "There is no precious resources (chunk: %s, %s) ", chunk.getX(), chunk.getZ()));
        }


        // send response to the player
        player.sendMessage(message.toString());
    }
}