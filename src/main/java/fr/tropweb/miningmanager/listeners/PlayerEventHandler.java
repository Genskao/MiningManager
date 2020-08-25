package fr.tropweb.miningmanager.listeners;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.data.Settings;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.MiningTask;
import fr.tropweb.miningmanager.pojo.PlayerLite;
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

    public PlayerEventHandler(Engine engine) {
        this.engine = engine;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event == null) return;
        if (event.getPlayer() == null) return;

        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(event.getPlayer());

        // if auto scan, if chunk different
        if (playerLite.isAutoScan() && !event.getFrom().getChunk().equals(event.getTo().getChunk()))
            this.engine.getChunkEngine().onCommandInChunkOfPlayer(event.getPlayer(), event.getTo().getChunk());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerSelectChest(PlayerInteractEvent event) {
        if (event == null) return;

        final Player player = event.getPlayer();
        if (player == null) return;

        final Block block = event.getClickedBlock();
        if (block == null) return;

        // get player from memory
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // get mining task from player
        final MiningTask miningTask = playerLite.getMiningTask();

        // if have the end task and if he doesn't choose chest
        if (miningTask.hasMiningTask() && !miningTask.hasMiningChest()) {

            // if it's chest
            if (this.engine.getBlockEngine().isChest(block)) {

                // add chest to player
                miningTask.setMiningChest(block);

                // cancel task
                miningTask.getMiningTask().cancel();

                // get settings
                final Settings settings = this.engine.getSettings();

                // create task
                final BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                        this.engine.getPlugin(),
                        () -> this.engine.getMiningEngine().startMining(player),
                        settings.getTickMiningStart(),
                        settings.getTickMiningInterval());

                // save task
                miningTask.setMiningTask(task);

                // inform player
                Utils.green(player, "Mining starting... loot every %ss.", settings.getMiningInterval());

                // cancel the click
                event.setCancelled(true);
            }
        }
    }
}
