package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.MiningManager;
import fr.tropweb.miningmanager.dao.BlockDAO;
import fr.tropweb.miningmanager.pojo.BlockData;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.EnumSet;

public final class BlockEngine {
    private final Engine engine;

    private final EnumSet<Material> preciousOre = EnumSet.noneOf(Material.class);
    private final EnumSet<Material> containers = EnumSet.noneOf(Material.class);
    private final EnumSet<Material> chests = EnumSet.noneOf(Material.class);
    private final EnumSet<Material> shulkerBox = EnumSet.noneOf(Material.class);
    private final EnumSet<Material> barrels = EnumSet.noneOf(Material.class);
    private final EnumSet<Material> minecartChest = EnumSet.noneOf(Material.class);

    private final BlockDAO<BlockData> blockDAO;

    public BlockEngine(final Engine engine) {
        this.engine = engine;

        // load the precious resource of world
        this.preciousOre.add(Material.COAL_ORE);
        this.preciousOre.add(Material.IRON_ORE);
        this.preciousOre.add(Material.GOLD_ORE);
        this.preciousOre.add(Material.REDSTONE_ORE);
        this.preciousOre.add(Material.LAPIS_ORE);
        this.preciousOre.add(Material.DIAMOND_ORE);
        this.preciousOre.add(Material.EMERALD_ORE);

        // load chests
        this.chests.add(Material.CHEST);
        this.chests.add(Material.TRAPPED_CHEST);

        // for the version spigot 1.11 (backward compatibility)
        if (MiningManager.checkVersion(1, 11)) {
            // load new chests
            this.shulkerBox.add(Material.LIME_SHULKER_BOX);
            this.shulkerBox.add(Material.BLACK_SHULKER_BOX);
            this.shulkerBox.add(Material.BLUE_SHULKER_BOX);
            this.shulkerBox.add(Material.BROWN_SHULKER_BOX);
            this.shulkerBox.add(Material.CYAN_SHULKER_BOX);
            this.shulkerBox.add(Material.GRAY_SHULKER_BOX);
            this.shulkerBox.add(Material.GREEN_SHULKER_BOX);
            this.shulkerBox.add(Material.LIGHT_BLUE_SHULKER_BOX);
            this.shulkerBox.add(Material.MAGENTA_SHULKER_BOX);
            this.shulkerBox.add(Material.ORANGE_SHULKER_BOX);
            this.shulkerBox.add(Material.PINK_SHULKER_BOX);
            this.shulkerBox.add(Material.YELLOW_SHULKER_BOX);
            this.shulkerBox.add(Material.WHITE_SHULKER_BOX);
            this.shulkerBox.add(Material.RED_SHULKER_BOX);
            this.shulkerBox.add(Material.PURPLE_SHULKER_BOX);
            this.shulkerBox.add(Material.PINK_SHULKER_BOX);
            this.shulkerBox.add(Material.LIGHT_BLUE_SHULKER_BOX);
        }

        // for the version spigot 1.13 (backward compatibility)
        if (MiningManager.checkVersion(1, 13)) {
            // load new nether ore
            this.preciousOre.add(Material.NETHER_QUARTZ_ORE);

            // minecart chest
            this.minecartChest.add(Material.CHEST_MINECART);

            // load new chest
            this.shulkerBox.add(Material.SHULKER_BOX);
            this.shulkerBox.add(Material.LIGHT_GRAY_SHULKER_BOX);
        }

        // before 1.13
        else {
            // QUARTZ_ORE does not exist anymore (now Material.NETHER_QUARTZ_ORE)
            this.preciousOre.add(Material.valueOf("QUARTZ_ORE"));

            // STORAGE_MINECART does not exist anymore (now Material.CHEST_MINECART)
            this.minecartChest.add(Material.valueOf("STORAGE_MINECART"));
        }

        // for the version spigot 1.14 (backward compatibility)
        if (MiningManager.checkVersion(1, 14)) {
            this.barrels.add(Material.BARREL);
        }

        // for the version spigot 1.16 (backward compatibility)
        if (MiningManager.checkVersion(1, 16)) {
            this.preciousOre.add(Material.NETHER_GOLD_ORE);
            this.preciousOre.add(Material.ANCIENT_DEBRIS);
        }

        // for the version spigot 1.17 (backward compatibility)
        if (MiningManager.checkVersion(1, 17)) {
            this.preciousOre.add(Material.COPPER_ORE);

            // deep slate version
            this.preciousOre.add(Material.DEEPSLATE_IRON_ORE);
            this.preciousOre.add(Material.DEEPSLATE_COPPER_ORE);
            this.preciousOre.add(Material.DEEPSLATE_GOLD_ORE);
            this.preciousOre.add(Material.DEEPSLATE_COAL_ORE);
            this.preciousOre.add(Material.DEEPSLATE_DIAMOND_ORE);
            this.preciousOre.add(Material.DEEPSLATE_REDSTONE_ORE);
            this.preciousOre.add(Material.DEEPSLATE_LAPIS_ORE);
            this.preciousOre.add(Material.DEEPSLATE_EMERALD_ORE);
        }

        this.containers.addAll(this.chests);
        this.containers.addAll(this.shulkerBox);
        this.containers.addAll(this.barrels);
        this.containers.addAll(this.minecartChest);

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
    public boolean isContainer(final Block block) {
        return this.containers.contains(block.getType());
    }

    public boolean isPrecious(final Block block) {
        return this.isPrecious(new BlockData(block));
    }

    public boolean isPrecious(final BlockData blockData) {
        return this.preciousOre.contains(blockData.getMaterial());
    }

    public void saveBlockBroken(final BlockData blockData) {

        // get block data from db
        final BlockData blockDataFromDB = this.blockDAO.select(blockData);

        // check if block not exists
        if (blockDataFromDB == null) {

            // apply type of change
            blockData.setPlacedByPlayer(false);

            // save block on db
            this.blockDAO.save(blockData);
        }

        // check if the block is placed block
        else if (blockDataFromDB.isPlacedByPlayer()) {

            // remove the block
            this.blockDAO.delete(blockDataFromDB);
        }
    }

    public void saveBlockPlaced(final BlockData blockData) {

        // if block not exists we have to save it
        if (!this.blockDAO.exist(blockData)) {

            // apply type of change
            blockData.setPlacedByPlayer(true);

            // save block
            this.blockDAO.save(blockData);
        }
    }

    public boolean isChest(final Block chest) {
        return this.chests.contains(chest.getType());
    }

    public boolean isShulkerBox(final Block shulkerBox) {
        return this.shulkerBox.contains(shulkerBox.getType());
    }

    public boolean isBarrel(final Block barrel) {
        return this.barrels.contains(barrel.getType());
    }

    public boolean isMinecartChest(final Block chest) {
        return this.minecartChest.contains(chest.getType());
    }
}
