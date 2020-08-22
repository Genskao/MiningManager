package fr.tropweb.miningmanager.pojo;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Data
public class BlockLite {
    private String world;
    private Integer x;
    private Integer y;
    private Integer z;
    private Material material;
    private Integer hashcode;
    private Boolean placedByPlayer;

    public BlockLite(Block block) {
        this.world = block.getWorld().getName();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.material = block.getType();
        this.placedByPlayer = false;
    }
}
