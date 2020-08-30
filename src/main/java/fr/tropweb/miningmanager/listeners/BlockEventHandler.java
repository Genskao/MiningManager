package fr.tropweb.miningmanager.listeners;

import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.BlockData;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventHandler implements Listener {
    private final Engine engine;

    public BlockEventHandler(Engine engine) {
        this.engine = engine;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        // check if there is an event
        if (event == null) return;

        // check if it's player event
        if (event.getPlayer() == null) return;

        // get block
        final Block block = event.getBlock();

        // if it's precious block
        if (this.engine.getBlockEngine().isPrecious(block)) {

            // save the block
            this.engine.getBlockEngine().saveBlockBroken(new BlockData(block));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockPlace(final BlockPlaceEvent event) {
        // check if there is an event
        if (event == null) return;

        // check if it's player event
        if (event.getPlayer() == null) return;

        // get block
        final Block block = event.getBlock();

        // if it's precious block
        if (this.engine.getBlockEngine().isPrecious(block)) {

            // save the block
            this.engine.getBlockEngine().saveBlockPlaced(new BlockData(block));
        }
    }
}
