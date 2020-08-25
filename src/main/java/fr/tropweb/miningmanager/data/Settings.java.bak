package fr.tropweb.miningmanager.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {
    private static final long TICK_IN_SECOND = 20L;

    private final Plugin plugin;
    private FileConfiguration fileConfiguration;

    public Settings(final Plugin plugin) {
        this.plugin = plugin;
        this.fileConfiguration = this.plugin.getConfig();
    }

    public void reload() {
        this.plugin.reloadConfig();
        this.fileConfiguration = this.plugin.getConfig();
    }

    public Long getMiningInterval() {
        return this.fileConfiguration.getLong("mining-interval");
    }

    public Long getMiningTimeout() {
        return this.fileConfiguration.getLong("mining-timeout");
    }

    public Long getMiningStart() {
        return this.fileConfiguration.getLong("mining-start");
    }

    public Boolean getSmiteWhileMining() {
        return this.fileConfiguration.getBoolean("mining-effect.smite");
    }

    public Boolean getExplosionWhileMining() {
        return this.fileConfiguration.getBoolean("mining-effect.explosion");
    }

    public Boolean getRegenerationActive() {
        return this.fileConfiguration.getBoolean("regeneration-active");
    }

    public synchronized void setRegenerationActive(final Boolean active) {
        this.fileConfiguration.set("regeneration-active", active);
        this.plugin.saveConfig();
    }

    public Long getRegenerationInterval() {
        return this.fileConfiguration.getLong("regeneration-interval");
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
