package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.data.BlockDAO;
import fr.tropweb.miningmanager.pojo.BlockLite;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class BlockEngine {
    private final Engine engine;

    private final Material[] preciousOre = new Material[10];
    private final Material[] chests = new Material[4];

    private final BlockDAO<BlockLite> blockDAO;

    public BlockEngine(Engine engine) {
        this.engine = engine;

        int preciousIndex = 0;

        // load the precious resource of world
        this.preciousOre[preciousIndex++] = Material.COAL_ORE;
        this.preciousOre[preciousIndex++] = Material.IRON_ORE;
        this.preciousOre[preciousIndex++] = Material.GOLD_ORE;
        this.preciousOre[preciousIndex++] = Material.REDSTONE_ORE;
        this.preciousOre[preciousIndex++] = Material.LAPIS_ORE;
        this.preciousOre[preciousIndex++] = Material.DIAMOND_ORE;
        this.preciousOre[preciousIndex++] = Material.EMERALD_ORE;

        // load the precious resource of nether
        this.preciousOre[preciousIndex++] = Material.NETHER_GOLD_ORE;
        this.preciousOre[preciousIndex++] = Material.NETHER_QUARTZ_ORE;
        this.preciousOre[preciousIndex++] = Material.ANCIENT_DEBRIS;

        int chestsIndex = 0;

        // load chests
        this.chests[chestsIndex++] = Material.CHEST;
        this.chests[chestsIndex++] = Material.BARREL;
        this.chests[chestsIndex++] = Material.TRAPPED_CHEST;
        this.chests[chestsIndex++] = Material.SHULKER_BOX;

        // reload data
        this.blockDAO = this.engine.getSqliteEngine().getBlockDAO();
    }

    private boolean contains(final Material material) {
        // search into the provided array
        for (int i = 0; i < this.preciousOre.length; i++) {

            // return true if enum is found
            if (this.preciousOre[i] == material)
                return true;
        }

        // return false by default
        return false;
    }

    public Material[] getPreciousOre() {
        return this.preciousOre;
    }

    /**
     * Check if the block is a chest.
     * <ul>
     *     <li>CHEST</li>
     *     <li>BARREL</li>
     *     <li>TRAPPED_CHEST</li>
     *     <li>SHULKER_BOX</li>
     * </ul>
     *
     * @param block the block to check
     * @return true if it's chest
     */
    public boolean isChest(final Block block) {
        for (int i = 0; i < this.chests.length; i++) {
            if (block.getType() == this.chests[i])
                return true;
        }
        return false;
    }

    public boolean isPrecious(Block block) {
        return this.isPrecious(new BlockLite(block));
    }

    public boolean isPrecious(BlockLite blockLite) {
        return this.contains(blockLite.getMaterial());
    }

    public void saveBlockBroken(BlockLite blockLite) {
        saveBlock(blockLite, false);
    }

    public void saveBlockPlaced(BlockLite blockLite) {
        saveBlock(blockLite, true);
    }

    public void saveBlock(final BlockLite blockLite, final boolean placed) {
        // if block not exists we have to save it
        if (!this.blockDAO.exist(blockLite)) {

            // apply type of change
            blockLite.setPlacedByPlayer(placed);

            // save block
            this.blockDAO.save(blockLite);
        }

        // if the block not exists and it's placed block we don't need to keep it
        else if (placed) {

            // check if the block is placed too
            final BlockLite block = this.blockDAO.select(blockLite);
            if (block.isPlacedByPlayer()) {

                // remove the block
                this.blockDAO.delete(blockLite);
            }
        }
    }
}
