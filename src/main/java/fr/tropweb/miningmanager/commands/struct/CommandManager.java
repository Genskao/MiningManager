package fr.tropweb.miningmanager.commands.struct;

import org.bukkit.ChatColor;

public enum CommandManager {
    SCAN(
            CommandContent.CMD_SCAN,
            CommandContent.CMD_SCAN_HELP,
            PermissionManager.SCAN),
    SCAN_AUTO(
            CommandContent.CMD_SCAN_AUTO,
            CommandContent.CMD_SCAN_AUTO_HELP,
            PermissionManager.SCAN_AUTO),
    MINING(
            CommandContent.CMD_MINING,
            CommandContent.CMD_MINING_HELP,
            PermissionManager.MINING),
    MINING_STOP(
            CommandContent.CMD_MINING_STOP,
            CommandContent.CMD_MINING_STOP_HELP,
            PermissionManager.MINING_STOP),
    MINING_SHOW(
            CommandContent.CMD_MINING_SHOW,
            CommandContent.CMD_MINING_SHOW_HELP,
            PermissionManager.MINING_SHOW),
    RELOAD(
            CommandContent.CMD_RELOAD,
            CommandContent.CMD_RELOAD_HELP,
            PermissionManager.RELOAD),
    REGENERATION(
            CommandContent.CMD_REGENERATION,
            CommandContent.CMD_REGENERATION_HELP,
            PermissionManager.REGENERATION),
    REGENERATION_STOP(
            CommandContent.CMD_REGENERATION_STOP,
            CommandContent.CMD_REGENERATION_STOP_HELP,
            PermissionManager.REGENERATION_STOP);

    private final String command;
    private final String help;
    private final PermissionManager permissionManager;

    CommandManager(final String command, final String help, final PermissionManager permissionManager) {
        this.command = command;
        this.help = ChatColor.BLUE + String.format(help, ChatColor.GOLD, ChatColor.GREEN);
        this.permissionManager = permissionManager;
    }

    public String getCommand() {
        return this.command;
    }

    public String getHelp() {
        return this.help;
    }

    public PermissionManager getPermissionManager() {
        return this.permissionManager;
    }
}
