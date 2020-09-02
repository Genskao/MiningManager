package fr.tropweb.miningmanager.commands.struct;

/**
 * Class to easy make multi-language.
 */
class CommandContent {
    protected static final String CMD_SCAN_HELP = "/mm%s scan%s provide all precious resources on the current chunk";
    protected static final String CMD_SCAN_AUTO_HELP = "/mm%s scan auto%s provide all precious resources when you move on other chunks";
    protected static final String CMD_MINING_HELP = "/mm%s mining%s extract all precious resources the chunk";
    protected static final String CMD_MINING_STOP_HELP = "/mm%s mining stop%s stop to extract all precious resources";
    protected static final String CMD_MINING_SHOW_HELP = "/mm%s mining show%s show all precious resources left";
    protected static final String CMD_RELOAD_HELP = "/mm%s reload%s reload the plugin data store";
    protected static final String CMD_REGENERATION_HELP = "/mm%s regeneration%s start slow regeneration of blocks mined by players";
    protected static final String CMD_REGENERATION_STOP_HELP = "/mm%s regeneration stop%s the regeneration of blocks mined by players";

    protected static final String CMD_SCAN = "scan";
    protected static final String CMD_SCAN_AUTO = "auto";
    protected static final String CMD_MINING = "mining";
    protected static final String CMD_MINING_STOP = "stop";
    protected static final String CMD_MINING_SHOW = "show";
    protected static final String CMD_RELOAD = "reload";
    protected static final String CMD_REGENERATION = "regeneration";
    protected static final String CMD_REGENERATION_STOP = "stop";
}
