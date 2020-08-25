package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.Mining;
import fr.tropweb.miningmanager.commands.Regeneration;
import fr.tropweb.miningmanager.commands.Reload;
import fr.tropweb.miningmanager.commands.Scan;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.dao.sqlite.SQLiteDAO;
import fr.tropweb.miningmanager.data.Settings;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import lombok.Data;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@Data
public final class Engine {
    private static final String VERSION_REGEX = ".*\\(MC\\: (\\d+.\\d+(.\\d+)*)\\)";

    private final HashMap<CommandManager, SubCommand> commands = new LinkedHashMap<>();

    private final Plugin plugin;
    private final Logger logger;

    private final Settings settings;
    private final SQLiteEngine sqliteEngine;

    private final ChunkEngine chunkEngine;
    private final BlockEngine blockEngine;
    private final PlayerEngine playerEngine;

    private final MiningEngine miningEngine;
    private final RegenerationEngine regenerationEngine;


    public Engine(final Plugin plugin, final Logger logger, final SQLiteDAO sqliteDAO) {
        this.plugin = plugin;
        this.logger = logger;

        this.settings = new Settings(this.plugin);
        this.sqliteEngine = new SQLiteEngine(sqliteDAO);

        this.chunkEngine = new ChunkEngine(this);
        this.blockEngine = new BlockEngine(this);
        this.playerEngine = new PlayerEngine(this);

        this.miningEngine = new MiningEngine(this);
        this.regenerationEngine = new RegenerationEngine(this);

        commands.clear();
        commands.put(CommandManager.SCAN, new Scan(this));
        commands.put(CommandManager.MINING, new Mining(this));
        commands.put(CommandManager.RELOAD, new Reload(this));
        commands.put(CommandManager.REGENERATION, new Regeneration(this));
    }

    public Server getServer() {
        return this.plugin.getServer();
    }

    public boolean hasVersion(final int major, final int minor) {
        // prepare information
        final String version = this.getServer().getVersion().replaceAll(VERSION_REGEX, "$1");
        final String[] split = version.split("\\.");

        // we should have 2 number
        if (split.length >= 2) {
            int currentMajor = Integer.valueOf(split[0]);
            int currentMinor = Integer.valueOf(split[1]);

            // check the version
            return currentMajor >= major && currentMinor >= minor;
        }

        // return false but default
        return false;
    }

    public void reload(Player player) {

        // stop all tasks
        this.stopTask();

        // clear player map
        this.getPlayerEngine().getPlayerLiteMap().clear();

        // start regenerate cron
        this.getRegenerationEngine().autoStart();

        // inform player
        Utils.green(player, "Reload complete.");
    }

    public void stopTask() {

        // search to player list
        for (final PlayerLite playerLite : this.getPlayerEngine().getPlayerLiteMap().values()) {

            // if player has mining task
            if (playerLite.hasMiningTask()) {

                // stop the mining of all player
                playerLite.stopMiningTask();

                // check if the player exist
                final Player player = this.getServer().getPlayer(playerLite.getUniqueId());
                if (player != null) {

                    // inform player concerns
                    Utils.red(player, "The plugin has been restart your mining has been stop.");
                }
            }
        }

        // stop regeneration
        this.getRegenerationEngine().stop(true);
    }
}
