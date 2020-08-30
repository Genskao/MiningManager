package fr.tropweb.miningmanager.data;

import org.bukkit.plugin.Plugin;

public class Settings extends AbstractSettings {

    public Settings(final Plugin plugin) {
        super(plugin);
    }

    public Long getMiningInterval() {
        return this.getLong(SettingsPath.MINING_INTERVAL);
    }

    public Long getMiningTimeout() {
        return this.getLong(SettingsPath.MINING_TIMEOUT);
    }

    public Long getMiningStart() {
        return this.getLong(SettingsPath.MINING_START);
    }

    public Boolean getSmiteWhileMining() {
        return this.getBoolean(SettingsPath.MINING_EFFECT_SMITE);
    }

    public Boolean getExplosionWhileMining() {
        return this.getBoolean(SettingsPath.MINING_EFFECT_EXPLOSION);
    }

    public Boolean getRegenerationActive() {
        return this.getBoolean(SettingsPath.REGENERATION_ACTIVE);
    }

    public void setRegenerationActive(final Boolean active) {
        this.save(SettingsPath.REGENERATION_ACTIVE, active);
    }

    public Long getRegenerationInterval() {
        return this.getLong(SettingsPath.REGENERATION_INTERVAL);
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

    public Double getScanPrice() {
        return this.getDouble(SettingsPath.SCAN_PRICE);
    }

    public double getMiningPrice() {
        return this.getDouble(SettingsPath.MINING_PRICE);
    }
}
