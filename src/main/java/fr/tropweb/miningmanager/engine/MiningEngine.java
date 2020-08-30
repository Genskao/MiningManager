package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.data.Settings;
import fr.tropweb.miningmanager.pojo.BlockLite;
import fr.tropweb.miningmanager.pojo.MiningTask;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MiningEngine {
    private static final int EXPLOSION_POWER = 0;

    private final Engine engine;
    private final BlockEngine blockEngine;

    public MiningEngine(Engine engine) {
        this.engine = engine;
        this.blockEngine = this.engine.getBlockEngine();
    }

    public void startMining(final Player player) {
        // get player from memory
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // get mining task from player
        final MiningTask miningTask = playerLite.getMiningTask();

        // player should not have choose a chest
        if (!miningTask.hasMiningChest()) {

            // inform player that there is no chest selected
            Utils.red(player, "The task has been canceled.");

            // stop tasks
            miningTask.stopMiningTask();

            // end
            return;
        }

        // get the chest block
        final Block container = miningTask.getMiningChest();

        // if there is no chest anymore stop task
        if (!this.engine.getBlockEngine().isContainer(container)) {

            // inform player
            Utils.red(player, "The task has been canceled because your chest has been destroy.");

            // stop tasks
            miningTask.stopMiningTask();

            // end
            return;
        }

        // pointer to list
        final List<Block> blocks = miningTask.getBlockToMine();

        // load setting
        final Settings settings = this.engine.getSettings();

        // we should have blocks
        if (this.hasBlocks(miningTask)) {

            // choose random block
            final int iBlock = getRandomInt(0, blocks.size());

            // retrieve block and cancel if it doesn't exist anymore
            final Block block = blocks.get(iBlock);
            if (!this.engine.getBlockEngine().isPrecious(block)) {

                // remove to the list
                blocks.remove(iBlock);

                // retake
                startMining(player);

                // end of recursive process
                return;
            }

            // retrieve the chest
            final Inventory inventory;
            if (this.blockEngine.isChest(container)) {
                final Chest chest = (Chest) container.getState();
                inventory = chest.getInventory();
            } else if (this.blockEngine.isMinecartChest(container)) {
                final StorageMinecart chest = (StorageMinecart) container.getState();
                inventory = chest.getInventory();
            } else if (this.blockEngine.isShulkerBox(container)) {
                final ShulkerBox chest = (ShulkerBox) container.getState();
                inventory = chest.getInventory();
            } else if (this.blockEngine.isBarrel(container)) {
                final Barrel chest = (Barrel) container.getState();
                inventory = chest.getInventory();
            } else
                throw new CommandException("The plugin don't know how to manage this chest, please report to the team.");

            // the inventory should be free
            if (this.engine.getPlayerEngine().isInventoryFree(inventory, block.getType())) {

                // give item to player
                inventory.addItem(new ItemStack(block.getType(), 1));

                // save block before extract if not placed by player
                this.engine.getBlockEngine().saveBlockBroken(new BlockLite(block));

                // retrieve the location
                final Location blockLocation = block.getLocation();

                // smite the block to have the effect :D
                if (settings.getSmiteWhileMining())
                    block.getWorld().strikeLightning(blockLocation);

                // explosion effect :D
                if (settings.getExplosionWhileMining())
                    block.getWorld().createExplosion(blockLocation, EXPLOSION_POWER);

                // replace block by airs
                block.setType(Material.AIR);

                // remove to the list
                blocks.remove(iBlock);

                // return if other block
                if (this.hasBlocks(miningTask)) return;
            } else {

                // cancel the task
                miningTask.stopMiningTask();

                // inform the player
                Utils.red(player, "Your chest is full, mining has been stopped.");

                // end
                return;
            }
        }

        // cancel the task
        miningTask.stopMiningTask();

        // inform the player
        Utils.green(player, "Your mining has been done.");
    }

    private boolean hasBlocks(final MiningTask miningTask) {

        // check if the list seems empty
        if (!miningTask.hasBlockToMine()) {

            // update the current task and check if no blocks was regenerated or added
            miningTask.setBlockToMine(this.engine.getChunkEngine().getBlockFromChunk(miningTask.getChunk()));

            // return true if there is block
            return !miningTask.getBlockToMine().isEmpty();
        }

        // there is blocks
        return true;
    }

    private static int getRandomInt(int min, int max) {

        // not max +1 it's for array
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
