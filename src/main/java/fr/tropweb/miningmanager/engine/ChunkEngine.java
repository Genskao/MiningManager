package fr.tropweb.miningmanager.engine;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public final class ChunkEngine {
    private static final int MAX_CHUNK_X = 15;
    private static final int MAX_CHUNK_Z = 15;
    private static final int MIN_CHUNK_Y = 1; // don't touch the bedrock at the y=1

    private final Engine engine;

    public ChunkEngine(final Engine engine) {
        this.engine = engine;
    }

    public int[] getMaterialAmount(final Chunk chunk) {

        // create list of materials found
        final int[] amount = new int[Material.values().length];

        // check all blocks
        for (final Block block : this.getBlockFromChunk(chunk)) {

            // feed the return array
            ++amount[block.getType().ordinal()];
        }

        // return array
        return amount;
    }

    public List<Block> getBlockFromChunk(final Chunk chunk) {

        // create block list
        final List<Block> blocks = new ArrayList<>();

        // max height of the world
        final int maxY = chunk.getWorld().getMaxHeight() - 1;
        for (int x = 0; x <= MAX_CHUNK_X; x++) {
            for (int y = MIN_CHUNK_Y; y <= maxY; y++) {
                for (int z = 0; z <= MAX_CHUNK_Z; z++) {

                    // get blocks from the x, y and z position
                    final Block block = chunk.getBlock(x, y, z);

                    // save if precious block to the list
                    if (this.engine.getBlockEngine().isPrecious(block)) blocks.add(block);
                }
            }
        }

        // return the block list
        return blocks;
    }
}
