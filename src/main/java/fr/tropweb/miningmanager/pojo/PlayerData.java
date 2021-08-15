package fr.tropweb.miningmanager.pojo;

import lombok.Data;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class PlayerData {
    private UUID uniqueId;
    private boolean autoScan;
    private MiningTask miningTask;

    public PlayerData(final Player player) {
        this.uniqueId = player.getUniqueId();
        this.miningTask = new MiningTask();
    }
}
