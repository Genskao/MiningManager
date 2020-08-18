package fr.tropweb.miningmanager.listeners;

import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.BlockLite;
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
    public void onBlockBreak(BlockBreakEvent event) {
        if (event == null) return;
        if (event.getPlayer() == null) return;

        final Block block = event.getBlock();
        if (this.engine.getBlockEngine().isPrecious(block))
            this.engine.getBlockEngine().saveBlockBroken(new BlockLite(block));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event == null) return;
        if (event.getPlayer() == null) return;

        final Block block = event.getBlock();
        if (this.engine.getBlockEngine().isPrecious(block))
            this.engine.getBlockEngine().saveBlockPlaced(new BlockLite(block));
    }
}
