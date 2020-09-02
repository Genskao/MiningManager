package fr.tropweb.miningmanager.listeners;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.Scan;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.MiningTask;
import fr.tropweb.miningmanager.pojo.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerEventHandler implements Listener {
    private final Engine engine;

    public PlayerEventHandler(final Engine engine) {
        this.engine = engine;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerMove(final PlayerMoveEvent event) {

        // event is expected
        if (event == null) return;

        // player is expected
        if (event.getPlayer() == null) return;

        // constant list
        final Player player = event.getPlayer();
        final PlayerData playerData = this.engine.getPlayerEngine().getPlayerLite(player);

        // check if auto scan = true, and if chunk is different
        if (playerData.isAutoScan() && !event.getFrom().getChunk().equals(event.getTo().getChunk())) {

            // check if economy plugin is enabled
            if (this.engine.getEconomyPlugin().isEnabled(this.engine.getSettings().getScanPrice())) {

                // check if the money of the player has been taken
                if (!this.engine.getEconomyPlugin().takeMoney(player, this.engine.getSettings().getScanPrice())) {

                    // unset auto scan
                    playerData.setAutoScan(false);

                    // inform player that he don't have enough money
                    Utils.red(player, "You don't have enough money to continue the auto scan.");

                    // stop process
                    return;
                }

                // economy message about the success
                Utils.green(player, "You have spent %s$ to start scan.", this.engine.getSettings().getScanPrice());
            }

            // scan the chunk's player
            Scan.scanChunkOfPlayer(this.engine, player, event.getTo().getChunk());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerSelectChest(final PlayerInteractEvent event) {

        // event is expected
        if (event == null) return;

        // player is expected
        final Player player = event.getPlayer();
        if (player == null) return;

        // clicked block is expected
        final Block container = event.getClickedBlock();
        if (container == null) return;

        // constant list
        final PlayerData playerData = this.engine.getPlayerEngine().getPlayerLite(player);
        final MiningTask miningTask = playerData.getMiningTask();

        // check if there is mining task running without selected chest
        if (miningTask.hasMiningTask() && !miningTask.hasMiningChest()) {

            // check if the selected block is a chest
            if (this.engine.getBlockEngine().isContainer(container)) {

                // configure chest for the player
                miningTask.setMiningChest(container);

                // cancel the starting task
                miningTask.getMiningTask().cancel();

                // create mining task
                final BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                        this.engine.getPlugin(),
                        () -> this.engine.getMiningEngine().startMining(player),
                        this.engine.getSettings().getTickMiningStart(),
                        this.engine.getSettings().getTickMiningInterval()
                );

                // save task to the player
                miningTask.setMiningTask(task);

                // inform player that the mining start has been started
                Utils.green(player, "Mining starting... loot every %ss.", this.engine.getSettings().getMiningInterval());

                // cancel the click event to avoid to open the chest
                event.setCancelled(true);
            }
        }
    }
}
