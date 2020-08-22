package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.data.BlockDAO;
import fr.tropweb.miningmanager.pojo.BlockLite;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class BlockEngine {
    private final Engine engine;

    private final Material[] preciousOre = new Material[10];
    private final Material[] chests = new Material[4];

    private final BlockDAO<BlockLite> liteBlockDAO;

    public BlockEngine(Engine engine) {
        this.engine = engine;

        // load the precious resource of world
        this.preciousOre[0] = Material.COAL_ORE;
        this.preciousOre[1] = Material.IRON_ORE;
        this.preciousOre[2] = Material.GOLD_ORE;
        this.preciousOre[3] = Material.REDSTONE_ORE;
        this.preciousOre[4] = Material.LAPIS_ORE;
        this.preciousOre[5] = Material.DIAMOND_ORE;
        this.preciousOre[6] = Material.EMERALD_ORE;

        // load the precious resource of nether
        this.preciousOre[7] = Material.NETHER_GOLD_ORE;
        this.preciousOre[8] = Material.NETHER_QUARTZ_ORE;
        this.preciousOre[9] = Material.ANCIENT_DEBRIS;


        // load chests
        this.chests[0] = Material.CHEST;
        this.chests[1] = Material.BARREL;
        this.chests[2] = Material.TRAPPED_CHEST;
        this.chests[3] = Material.SHULKER_BOX;

        // reload data
        this.liteBlockDAO = this.engine.getSqliteEngine().getBlockDAO();
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

        // if block not exists
        if (!this.liteBlockDAO.exist(blockLite)) {

            // apply type of change
            blockLite.setPlacedByPlayer(placed);

            // save block
            this.liteBlockDAO.save(blockLite);
        }
    }
}
