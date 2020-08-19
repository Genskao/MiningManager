package fr.tropweb.miningmanager.commands.struct;

import org.bukkit.ChatColor;

public enum CommandManager {
    SCAN("scan", "scan.one", "/mm%s scan%s provide all precious resources on the current chunk"),
    AUTO_SCAN("auto", "scan.auto", "/mm%s scan auto%s provide all precious resources when you move on other chunks"),
    MINING("mining", "mining.one", "/mm%s mining%s extract all precious resources the chunk"),
    STOP_MINING("stop", "mining.stop", "/mm%s mining stop%s stop to extract all precious resources"),
    SHOW_MINING("show", "mining.show", "/mm%s mining show%s show all precious resources left"),
    RELOAD("reload", "reload.only", "/mm%s reload%s reload the plugin data store"),
    RELOAD_AND_DELETE("delete", "reload.delete", "/mm%s reload delete%s reload the plugin data store and delete duplicated/corrupt data"),
    REGENERATE("regenerate", "regenerate", "/mm%s regenerate%s start slow regeneration of blocks mined by players");

    private final String command;
    private final String permission;
    private final String help;

    CommandManager(final String command, final String permission, final String help) {
        this.command = command;
        this.permission = String.format("mm.%s", permission);
        this.help = ChatColor.BLUE + String.format(help, ChatColor.GOLD, ChatColor.GREEN);
    }

    public String getCommand() {
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String getHelp() {
        return help;
    }
}
