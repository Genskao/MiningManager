package fr.tropweb.miningmanager.data;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
    private static final long TICK_IN_SECOND = 20L;

    private final FileConfiguration fileConfiguration;

    public Settings(final FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public Long getMiningInterval() {
        return fileConfiguration.getLong("mining-interval");
    }

    public Long getMiningTimeout() {
        return fileConfiguration.getLong("mining-timeout");
    }

    public Long getMiningStart() {
        return fileConfiguration.getLong("mining-start");
    }

    public Boolean getSmiteWhileMining() {
        return fileConfiguration.getBoolean("mining-effect.smite");
    }

    public Boolean getExplosionWhileMining() {
        return fileConfiguration.getBoolean("mining-effect.explosion");
    }

    public Boolean getRegenerationActive() {
        return fileConfiguration.getBoolean("regeneration-active");
    }

    public void setRegenerationActive(final Boolean active) {
        fileConfiguration.set("regeneration-active", active);
    }

    public Long getRegenerationInterval() {
        return fileConfiguration.getLong("regeneration-interval");
    }

    public Long getTickMiningInterval() {
        return this.getMiningInterval() * TICK_IN_SECOND;
    }

    public Long getTickMiningStart() {
        return this.getMiningStart() * TICK_IN_SECOND;
    }

    public Long getTickMiningTimeout() {
        return this.getMiningTimeout() * TICK_IN_SECOND;
    }

    public Long getTickRegenerateInterval() {
        return this.getRegenerationInterval() * TICK_IN_SECOND;
    }
}
