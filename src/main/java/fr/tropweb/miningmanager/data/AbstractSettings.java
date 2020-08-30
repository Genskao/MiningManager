package fr.tropweb.miningmanager.data;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.NumberConversions;

import java.util.EnumMap;

import static fr.tropweb.miningmanager.data.SettingsPath.*;

public class AbstractSettings {
    protected static final long TICK_IN_SECOND = 20L;

    private final EnumMap<SettingsPath, SettingsPath> settingsDeprecated = new EnumMap(SettingsPath.class);
    private final Plugin plugin;

    public AbstractSettings(final Plugin plugin) {
        this.plugin = plugin;

        // deprecated configuration
        this.settingsDeprecated.put(MINING_INTERVAL, OLD_MINING_INTERVAL);
        this.settingsDeprecated.put(MINING_TIMEOUT, OLD_MINING_TIMEOUT);
        this.settingsDeprecated.put(MINING_START, OLD_MINING_START);
        this.settingsDeprecated.put(MINING_EFFECT_SMITE, OLD_MINING_EFFECT_SMITE);
        this.settingsDeprecated.put(MINING_EFFECT_EXPLOSION, OLD_MINING_EFFECT_EXPLOSION);
        this.settingsDeprecated.put(REGENERATION_ACTIVE, OLD_REGENERATION_ACTIVE);
        this.settingsDeprecated.put(REGENERATION_INTERVAL, OLD_REGENERATION_INTERVAL);
    }

    public void reload() {
        this.plugin.reloadConfig();
    }

    protected Long getLong(final SettingsPath settingsPath) {
        return NumberConversions.toLong(this.get(settingsPath));
    }

    protected Double getDouble(final SettingsPath settingsPath) {
        return NumberConversions.toDouble(this.get(settingsPath));
    }

    protected Boolean getBoolean(final SettingsPath settingsPath) {
        return (Boolean) this.get(settingsPath);
    }

    protected Object get(final SettingsPath settingsPath) {

        // check if deprecated configuration exists
        if (this.settingsDeprecated.containsKey(settingsPath)) {

            // get the old settings
            final SettingsPath oldPath = this.settingsDeprecated.get(settingsPath);

            // get the value of the old settings
            final Object value = this.plugin.getConfig().get(oldPath.getPath());

            // check if there is value
            if (value != null) {

                // save the old value on the new settings
                this.save(settingsPath, value);

                // remove the old settings
                this.remove(oldPath);
            }
        }

        // return the value of the settings
        return this.plugin.getConfig().get(settingsPath.getPath());
    }

    protected synchronized void save(final SettingsPath settingsPath, final Object o) {
        this.plugin.getConfig().set(settingsPath.getPath(), o);
        this.plugin.saveConfig();
    }

    protected synchronized void remove(final SettingsPath settingsPath) {
        this.plugin.getConfig().set(settingsPath.getPath(), null);
    }
}
