package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.MiningTask;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

import static fr.tropweb.miningmanager.commands.struct.CommandManager.*;

public class Mining implements SubCommand {
    private final Engine engine;

    public Mining(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {

        // get player data from memory
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // get mining task from player
        final MiningTask miningTask = playerLite.getMiningTask();

        // get chunk of player
        final Chunk chunk = player.getLocation().getChunk();

        // check if player want to stop the current mining
        if (attribute == STOP_MINING) {

            // check if mining task running
            if (!miningTask.hasMiningTask()) {
                throw new CommandException("Mining is not running.");
            }

            // stop mining task
            miningTask.stopMiningTask();

            // inform player if the task end properly
            Utils.green(player, "Mining has been stopped.");
        }

        // check if the player want the number of precious resource left
        else if (attribute == SHOW_MINING) {

            // check if mining task running
            if (!miningTask.hasMiningTask()) {
                throw new CommandException("Mining is not running.");
            }

            // check if player have mining block left
            if (miningTask.hasBlockToMine()) {

                // start free scan for the player
                Scan.scanChunkOfPlayer(this.engine, player, miningTask.getChunk());
            }
        }

        // enter to the mining case
        else {

            // check if mining task running
            if (miningTask.hasMiningTask()) {

                // player cannot have two mining task
                throw new CommandException("You cannot run two mining at the same time.");
            }

            // check if other player already have start mining task in this chunk
            else if (this.engine.getPlayerEngine().isChunkAlreadyMined(chunk)) {

                // two mining task cannot start at the same chunk
                throw new CommandException("Another player is already mining out this chunk.");
            }

            // clear block list of the player
            miningTask.getBlockToMine().clear();

            // list the precious block of the chunk and add it to the player block list
            miningTask.getBlockToMine().addAll(this.engine.getChunkEngine().getBlockFromChunk(chunk));

            // check if there is block to mine
            if (!miningTask.hasBlockToMine()) {

                // we should not have empty block list
                throw new CommandException("There is no precious block to mine.");
            }

            // check if economy plugin is enabled
            if (this.economyEnabled()) {

                // check if player can pay and take money
                if (this.engine.getEconomyPlugin().takeMoney(player, this.engine.getSettings().getMiningPrice())) {

                    // inform player about the money taken
                    Utils.green(player, "You have spend %s$ to start the mining.", this.engine.getSettings().getMiningPrice());
                } else {

                    // if player don't have enough money
                    throw new CommandException("You don't have enough money to start mining");
                }
            }

            // The player have 15 (by default) to select a chest
            final BukkitTask task = Bukkit.getScheduler().runTaskLater(
                    this.engine.getPlugin(),
                    () -> this.stopMining(player, miningTask),
                    this.engine.getSettings().getTickMiningTimeout()
            );

            // the task is keep in memory
            miningTask.setMiningTask(task);

            // keep in mind the chunk
            miningTask.setChunk(chunk);

            // inform the player about the delay
            Utils.green(player, "Right click to the chest to start the mining. This action will be close in %ss.", this.engine.getSettings().getMiningTimeout());
        }
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

    private void stopMining(final Player player, final MiningTask miningTask) {

        // check if economy plugin is enabled
        if (this.economyEnabled()) {

            // money get back to the player
            this.engine.getEconomyPlugin().giveMoney(player, this.engine.getSettings().getMiningPrice());

            // inform about money
            Utils.green(player, "You retrieve %s$.", this.engine.getSettings().getMiningPrice());
        }

        // stop current task
        miningTask.stopMiningTask();

        // inform about abort
        Utils.red(player, "You mining task has been abort.");
    }

    private boolean economyEnabled() {
        // check if economy plugin is enabled and if there is price
        return this.engine.getEconomyPlugin().isEnabled(this.engine.getSettings().getMiningPrice());
    }
}
