package fr.tropweb.miningmanager.pojo;

import lombok.Data;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

@Data
public class MiningTask {
    private Chunk chunk;
    private BukkitTask miningTask;
    private Block miningChest;

    private List<Block> blockToMine = new ArrayList<>();

    public boolean hasMiningTask() {
        return this.miningTask != null && !this.miningTask.isCancelled();
    }

    public boolean hasMiningChest() {
        return this.miningChest != null;
    }

    public void stopMiningTask() {
        // if mining task running
        if (this.hasMiningTask()) {

            // cancel the task
            this.miningTask.cancel();

            // unset the task
            this.miningTask = null;
        }

        // clear the chest
        this.miningChest = null;

        // clear block
        this.blockToMine.clear();
    }

    public boolean hasBlockToMine() {
        return this.blockToMine != null && !this.blockToMine.isEmpty();
    }
}
