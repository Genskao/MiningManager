package fr.tropweb.miningmanager.commands.struct;

public enum PermissionManager {

    // command permission
    SCAN("scan"),
    SCAN_AUTO("scan.auto"),
    MINING("mining"),
    MINING_STOP("mining.stop"),
    MINING_SHOW("mining.show"),
    RELOAD("reload"),
    REGENERATION("regeneration"),
    REGENERATION_STOP("regeneration.stop"),


    // usage permission
    IGNORE_PRICE("ignore.price"),
    IGNORE_TOWNY("ignore.towny");

    private final String permission;

    PermissionManager(final String permission) {
        this.permission = new StringBuilder("mm").append(permission).toString();
    }

    public String getPermission() {
        return permission;
    }
}
