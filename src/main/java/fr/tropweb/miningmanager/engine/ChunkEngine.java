package fr.tropweb.miningmanager.engine;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.EnumSet;

public final class ChunkEngine extends Thread {
    private static final int MAX_CHUNK_X = 15;
    private static final int MAX_CHUNK_Z = 15;
    private static final int MIN_CHUNK_Y = 1; // don't touch the bedrock at the y=1

    private final Engine engine;

    public ChunkEngine(Engine engine) {
        this.engine = engine;
    }

    public boolean onCommandInChunkOfPlayer(Player player) {
        return onCommandInChunkOfPlayer(null, player, player.getLocation().getChunk());
    }

    public boolean onCommandInChunkOfPlayer(Player player, Chunk chunk) {
        return onCommandInChunkOfPlayer(null, player, chunk);
    }

    public boolean onCommandInChunkOfPlayer(EventEngine autoExtract, Player player) {
        return onCommandInChunkOfPlayer(autoExtract, player, player.getLocation().getChunk());
    }

    public boolean onCommandInChunkOfPlayer(EventEngine autoExtract, Player player, Chunk chunk) {
        long time = System.nanoTime();
        final int[] amount = getMaterialAmount(player, chunk, autoExtract);
        time = System.nanoTime() - time;

        double elapsedTimeInSecond = (double) time / 1_000_000;
        message(player, chunk, amount, "in %s ms", elapsedTimeInSecond);

        return true;
    }

    private void message(Player player, Chunk chunk, int[] amount, String end, double args) {

        // check the precious resources
        final EnumSet<Material> preciousOre = this.engine.getBlockEngine().getPreciousOre();

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

        // if there is resources add information
        if (resources.length() > 0) {
            message.append(String.format(ChatColor.BLUE + "You have found these precious resources (chunk: %s, %s):\n", chunk.getX(), chunk.getZ()));
            message.append(resources);
        }

        // inform the chuck is empty
        else {
            message.append(String.format(ChatColor.GRAY + "There is no precious resources (chunk: %s, %s) ", chunk.getX(), chunk.getZ()));
        }

        // if the command is heavy, player get log
        if (args > 50D) {
            player.sendMessage(message.toString() + String.format(end, args));
        }

        // else the response is normal
        else {
            player.sendMessage(message.toString());
        }
    }

    private int[] getMaterialAmount(Player player, final Chunk chunk, EventEngine autoExtract) {
        // create list of materials found
        final int[] amount = new int[Material.values().length];

        // max height of the world
        final int maxY = chunk.getWorld().getMaxHeight() - 1;
        for (int x = 0; x <= MAX_CHUNK_X; x++) {
            for (int y = MIN_CHUNK_Y; y <= maxY; y++) {
                for (int z = 0; z <= MAX_CHUNK_Z; z++) {

                    // get blocks
                    final Block block = chunk.getBlock(x, y, z);

                    // if we extract and if the block is knows
                    if (this.engine.getBlockEngine().isPrecious(block)) {
                        // add block to the list
                        ++amount[block.getType().ordinal()];

                        // run action
                        if (autoExtract != null) {
                            autoExtract.onBlock(player, block);
                        }
                    }
                }
            }
        }
        return amount;
    }
}
