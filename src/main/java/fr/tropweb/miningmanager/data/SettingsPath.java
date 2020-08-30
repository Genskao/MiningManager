package fr.tropweb.miningmanager.data;

public enum SettingsPath {
    @Deprecated
    OLD_MINING_INTERVAL("mining-interval"),
    @Deprecated
    OLD_MINING_TIMEOUT("mining-timeout"),
    @Deprecated
    OLD_MINING_START("mining-start"),
    @Deprecated
    OLD_MINING_EFFECT_SMITE("mining-effect.smite"),
    @Deprecated
    OLD_MINING_EFFECT_EXPLOSION("mining-effect.explosion"),
    @Deprecated
    OLD_REGENERATION_ACTIVE("regeneration-active"),
    @Deprecated
    OLD_REGENERATION_INTERVAL("regeneration-interval"),

    MINING_INTERVAL("mining.interval"),
    MINING_TIMEOUT("mining.timeout"),
    MINING_START("mining.start"),
    MINING_EFFECT_SMITE("mining.effect.smite"),
    MINING_EFFECT_EXPLOSION("mining.effect.explosion"),
    REGENERATION_ACTIVE("regeneration.active"),
    REGENERATION_INTERVAL("regeneration.interval"),

    SCAN_PRICE("scan.price"),
    MINING_PRICE("mining.price");

    private final String path;

    SettingsPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
