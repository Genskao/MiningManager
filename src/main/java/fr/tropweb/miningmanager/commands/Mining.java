package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.engine.EventEngine;
import fr.tropweb.miningmanager.data.Settings;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

import static fr.tropweb.miningmanager.commands.struct.CommandManager.*;

public class Mining extends EventEngine implements SubCommand {
    private final Engine engine;
    private final Plugin plugin;

    public Mining(Engine engine) {
        this.engine = engine;
        this.plugin = this.engine.getPlugin();
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {

        // get player data
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // check if player want to stop
        if (attribute == STOP_MINING) {

            // check if mining task running
            if (!playerLite.hasMiningTask()) {
                throw new CommandException("Mining is not running.");
            }

            // stop mining task
            playerLite.stopMiningTask();

            // inform player
            Utils.green(player, "Mining has been stopped.");

            // end
            return;
        }

        // return to the player the number of precious resource left
        else if (attribute == SHOW_MINING) {

            // check if mining task running
            if (!playerLite.hasMiningTask()) {
                throw new CommandException("Mining is not running.");
            }

            // if there is block to mine
            if (playerLite.hasBlockToMine()) {

                // scan for the player
                this.engine.getChunkEngine().onCommandInChunkOfPlayer(player, playerLite.getBlockToMine().get(0).getChunk());
            }

            // end
            return;
        }

        // if player already have mining task
        else if (playerLite.hasMiningTask()) {
            throw new CommandException("You cannot run two mining at the same time.");
        }

        // run scan of the chunk and collect info
        this.engine.getChunkEngine().onCommandInChunkOfPlayer(this, player);

        // if there is block
        if (!playerLite.hasBlockToMine()) {
            Utils.red(player, "There is no block to mine.");
            return;
        }

        // get settings
        final Settings settings = this.engine.getSettings();

        // task to leave in 15s
        final BukkitTask task = Bukkit.getScheduler().runTaskLater(this.engine.getPlugin(), () -> this.stopMining(player, playerLite), settings.getTickMiningTimeout());

        // save task
        playerLite.setBukkitTask(task);

        // inform the player
        Utils.green(player, "Right click to the chest to start the mining. This action will be close in %ss.", settings.getMiningTimeout());
    }

    @Override
    public CommandManager help() {
        return MINING;
    }

    @Override
    public CommandManager permission() {
        return MINING;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList(STOP_MINING, SHOW_MINING);
    }

    @Override
    public void onBlock(Player player, Block block) {

        // add info for the player
        this.engine.getPlayerEngine().getPlayerLite(player).getBlockToMine().add(block);
    }

    private void stopMining(Player player, PlayerLite playerLite) {
        // check if mining task
        if (playerLite.hasMiningTask()) {

            // stop current task
            playerLite.stopMiningTask();

            // inform
            Utils.red(player, "You mining task has been abort.");
        }
    }
}
