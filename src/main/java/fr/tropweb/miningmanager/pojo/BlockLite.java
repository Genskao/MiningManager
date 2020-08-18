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

    @Override
    public int hashCode() {
        if (hashcode == null) {
            hashcode = (world + x + y + z).hashCode();
        }
        return hashcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BlockLite blockLite = (BlockLite) o;
        return blockLite.hashcode == this.hashcode;
    }

    public boolean isEmpty() {
        return this.world == null || this.x == null || this.y == null || this.z == null
                || this.material == null || this.placedByPlayer == null;
    }
}
