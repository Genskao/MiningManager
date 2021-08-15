package fr.tropweb.miningmanager.pojo;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Data
public class BlockData {
    private String world;
    private int x;
    private int y;
    private int z;
    private Material material;
    private boolean placedByPlayer;
    private boolean blocked;

    public BlockData() {
        // do nothing
    }

    public BlockData(final Block block) {
        this.world = block.getWorld().getName();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.material = block.getType();
        this.placedByPlayer = false;
        this.blocked = false;
    }
}
