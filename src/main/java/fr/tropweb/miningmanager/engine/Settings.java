package fr.tropweb.miningmanager.engine;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class Settings {
    private static final long TICK_IN_SECOND = 20L;

    private final Long miningInterval;
    private final Long miningTimeout;
    private final Long miningStart;
    private final Boolean smiteWhileMining;
    private final Boolean explosionWhileMining;

    public Settings(FileConfiguration fileConfiguration) {
        this.miningInterval = fileConfiguration.getLong("mining-interval");
        this.miningTimeout = fileConfiguration.getLong("mining-timeout");
        this.miningStart = fileConfiguration.getLong("mining-start");
        this.smiteWhileMining = fileConfiguration.getBoolean("mining-effect.smite");
        this.explosionWhileMining = fileConfiguration.getBoolean("mining-effect.explosion");
    }

    public Long getTickMiningInterval() {
        return this.miningInterval * TICK_IN_SECOND;
    }

    public Long getTickMiningStart() {
        return this.miningStart * TICK_IN_SECOND;
    }

    public Long getTickMiningTimeout() {
        return this.miningTimeout * TICK_IN_SECOND;
    }
}
