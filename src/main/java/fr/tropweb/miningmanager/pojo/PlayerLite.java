package fr.tropweb.miningmanager.pojo;

import lombok.Data;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerLite {
    private UUID uniqueId;
    private boolean autoScan;

    private List<Block> blockToMine = new ArrayList<>();
    private BukkitTask bukkitTask;
    private Block miningChest;

    public PlayerLite(Player player) {
        this.uniqueId = player.getUniqueId();
    }

    public boolean hasMiningTask() {
        return this.bukkitTask != null && !this.bukkitTask.isCancelled();
    }

    public boolean hasChooseChest() {
        return miningChest != null;
    }

    public void stopMiningTask() {

        // if mining task running
        if (hasMiningTask()) {

            // cancel the task
            this.bukkitTask.cancel();

            // unset the task
            this.bukkitTask = null;
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
