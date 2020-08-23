package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.data.BlockDAO;
import fr.tropweb.miningmanager.pojo.BlockLite;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.EnumSet;

public final class BlockEngine {
    private final Engine engine;

    private final EnumSet<Material> preciousOre = EnumSet.noneOf(Material.class);
    private final EnumSet<Material> chests = EnumSet.noneOf(Material.class);

    private final BlockDAO<BlockLite> blockDAO;

    public BlockEngine(Engine engine) {
        this.engine = engine;

        // load the precious resource of world
        this.preciousOre.add(Material.COAL_ORE);
        this.preciousOre.add(Material.IRON_ORE);
        this.preciousOre.add(Material.GOLD_ORE);
        this.preciousOre.add(Material.REDSTONE_ORE);
        this.preciousOre.add(Material.LAPIS_ORE);
        this.preciousOre.add(Material.DIAMOND_ORE);
        this.preciousOre.add(Material.EMERALD_ORE);

        // load the precious resource of nether
        this.preciousOre.add(Material.NETHER_QUARTZ_ORE);

        // for the version spigot 1.16 (backward compatibility)
        if (this.engine.hasVersion(1, 16)) {
            this.preciousOre.add(Material.NETHER_GOLD_ORE);
            this.preciousOre.add(Material.ANCIENT_DEBRIS);
        }

        // load chests
        this.chests.add(Material.CHEST);
        this.chests.add(Material.TRAPPED_CHEST);
        this.chests.add(Material.SHULKER_BOX);

        // for the version spigot 1.14 (backward compatibility)
        if (this.engine.hasVersion(1, 14)) {
            this.chests.add(Material.BARREL);
        }

        // reload data
        this.blockDAO = this.engine.getSqliteEngine().getBlockDAO();
    }

    public EnumSet<Material> getPreciousOre() {
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
        return this.chests.contains(block.getType());
    }

    public boolean isPrecious(final Block block) {
        return this.isPrecious(new BlockLite(block));
    }

    public boolean isPrecious(final BlockLite blockLite) {
        return this.preciousOre.contains(blockLite.getMaterial());
    }

    public void saveBlockBroken(final BlockLite blockLite) {
        saveBlock(blockLite, false);
    }

    public void saveBlockPlaced(final BlockLite blockLite) {
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
