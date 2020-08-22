package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.Mining;
import fr.tropweb.miningmanager.commands.Regenerate;
import fr.tropweb.miningmanager.commands.Reload;
import fr.tropweb.miningmanager.commands.Scan;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.data.sqlite.SQLiteDAO;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@Data
public final class Engine {
    private final HashMap<CommandManager, SubCommand> commands = new LinkedHashMap<>();

    private final Plugin plugin;
    private final Logger logger;

    private final SQLiteEngine sqliteEngine;

    private final ChunkEngine chunkEngine;
    private final BlockEngine blockEngine;
    private final PlayerEngine playerEngine;

    private final Settings settings;

    public Engine(final Plugin plugin, final Logger logger, final SQLiteDAO sqliteDAO) {
        this.plugin = plugin;
        this.logger = logger;

        this.sqliteEngine = new SQLiteEngine(sqliteDAO);

        this.chunkEngine = new ChunkEngine(this);
        this.blockEngine = new BlockEngine(this);
        this.playerEngine = new PlayerEngine(this);

        this.settings = new Settings(this.plugin.getConfig());

        commands.clear();
        commands.put(CommandManager.SCAN, new Scan(this));
        commands.put(CommandManager.MINING, new Mining(this));
        commands.put(CommandManager.RELOAD, new Reload(this));
        commands.put(CommandManager.REGENERATE, new Regenerate(this));
    }

    public void reload(Player player) {

        // search to player list
        for (final PlayerLite playerLite : this.getPlayerEngine().getPlayerLiteMap().values()) {

            // if player has mining task
            if (playerLite.hasMiningTask()) {

                // stop the mining of all player
                playerLite.stopMiningTask();

                // inform player concerns
                Utils.red(player, "The plugin has been restart your mining has been stop.");
            }
        }

        // clear player map
        this.getPlayerEngine().getPlayerLiteMap().clear();

        // inform player
        Utils.green(player, "Reload complete.");
    }
}
