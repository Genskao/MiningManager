package fr.tropweb.miningmanager;

import fr.tropweb.miningmanager.data.DataStorage;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.listeners.BlockEventHandler;
import fr.tropweb.miningmanager.listeners.PlayerEventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MiningManager extends JavaPlugin {
    private Logger log;
    private DataStorage dataStorage;
    private Engine engine;

    public MiningManager() {
        // do nothing
    }

    public static void main(String[] args) {
        System.out.println("Please put this jar file in your /plugins/ folder.");
        System.exit(0);
    }

    @Override
    public void onLoad() {
        // Load the logger
        log = this.getLogger();
        this.log.setLevel(Level.INFO);

        this.log.info("On load started");
    }

    @Override
    public void onEnable() {
        this.log.info("onEnable...");

        this.log.info("DataStorage loading...");
        this.dataStorage = new DataStorage(this);

        this.log.info("Configuration loading...");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.log.info("Engine loading...");
        this.engine = new Engine(this, this.log, this.dataStorage);

        // enable command mine
        this.getCommand("mm").setExecutor(new CommandHandler(this.engine));
        this.getCommand("mm").setTabCompleter(new CompletionHandler(this.engine));

        // enable event
        final PluginManager pluginManager = this.getServer().getPluginManager();

        // when block is changed
        pluginManager.registerEvents(new BlockEventHandler(this.engine), this);

        // when player moved
        pluginManager.registerEvents(new PlayerEventHandler(this.engine), this);
    }

    @Override
    public void onDisable() {
        this.log.info("onDisable...");
        this.dataStorage = null;
        this.engine = null;
    }
}