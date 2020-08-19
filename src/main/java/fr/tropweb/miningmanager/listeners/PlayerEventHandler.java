package fr.tropweb.miningmanager.listeners;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.threads.MiningThread;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.engine.Settings;
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

        // get player
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // if have the end task and if he doesn't choose chest
        if (playerLite.hasMiningTask() && !playerLite.hasChooseChest()) {

            // if it's chest
            if (this.engine.getBlockEngine().isChest(block)) {

                // add chest to player
                playerLite.setMiningChest(block);

                // cancel task
                playerLite.getBukkitTask().cancel();

                // get settings
                final Settings settings = this.engine.getSettings();

                // create task
                final BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                        this.engine.getPlugin(),
                        () -> new MiningThread(this.engine, player).run(),
                        settings.getTickMiningStart(),
                        settings.getTickMiningInterval());

                // save task
                playerLite.setBukkitTask(task);

                // inform player
                Utils.green(player, "Mining starting... loot every %ss.", settings.getMiningInterval());

                // cancel the click
                event.setCancelled(true);
            }
        }
    }
}
