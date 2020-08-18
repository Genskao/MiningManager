package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.pojo.BlockLite;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;

public final class BlockEngine {
    private final Engine engine;

    private final HashSet<BlockLite> blockPlaced = new HashSet<>();
    private final HashSet<BlockLite> blockBroken = new HashSet<>();
    private final Material[] preciousOre = new Material[10];
    private final Material[] chests = new Material[4];

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
        this.reload();
    }

    public static boolean contains(final HashSet<BlockLite> sets, final BlockLite blockLite) {
        for (final BlockLite set : sets) {
            if (blockLite.hashCode() == set.hashCode()) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(final Material material) {
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

    public void reload() {
        reload(false);
    }

    public void reload(boolean delete) {

        // load all blocks placed by the player
        this.blockPlaced.clear();
        this.blockPlaced.addAll(this.engine.getDataStorage().reload(true, delete));

        // load all blocks break by the player
        this.blockBroken.clear();
        this.blockBroken.addAll(this.engine.getDataStorage().reload(false, delete));

        // case should never happen...
        checkCoherence(delete);
    }

    private void checkCoherence(boolean delete) {
        for (final BlockLite blockLiteBroken : this.blockBroken) {
            for (final BlockLite blockLitePlaced : this.blockPlaced) {
                if (blockLiteBroken.hashCode() == blockLitePlaced.hashCode()) {
                    this.engine.getLogger().info("This broken block already is in conflict with the a block placed by a player: " + blockLiteBroken.toString());
                    if (delete)
                        this.engine.getDataStorage().deleteBlock(blockLitePlaced);
                }
            }
        }
    }

    public void saveBlockBroken(BlockLite blockLite) {
        if (!this.hasBeenPlaced(blockLite) && !this.hasBeenBroken(blockLite)) {
            blockLite.setPlacedByPlayer(false);
            this.engine.getDataStorage().saveBlockRemoved(blockLite);
            this.blockBroken.add(blockLite);
        }
    }

    public void saveBlockPlaced(BlockLite blockLite) {
        if (!this.hasBeenBroken(blockLite) && !this.hasBeenPlaced(blockLite)) {
            blockLite.setPlacedByPlayer(true);
            this.engine.getDataStorage().saveBlockPlaced(blockLite);
            this.blockPlaced.add(blockLite);
        }
    }

    public boolean hasBeenBroken(final BlockLite blockLite) {
        return contains(this.blockBroken, blockLite);
    }

    public boolean hasBeenPlaced(final BlockLite blockLite) {
        return contains(this.blockPlaced, blockLite);
    }
}
