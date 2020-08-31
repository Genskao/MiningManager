package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.MiningManager;
import fr.tropweb.miningmanager.data.Settings;
import fr.tropweb.miningmanager.pojo.BlockData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandException;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class RegenerationEngine {
    private static BukkitTask task;

    private final Engine engine;
    private final SQLiteEngine sqLiteEngine;
    private final Settings settings;

    public RegenerationEngine(final Engine engine) {
        this.engine = engine;
        this.settings = this.engine.getSettings();
        this.sqLiteEngine = this.engine.getSqliteEngine();
    }

    public void autoStart() {
        if (this.settings.getRegenerationActive())
            start();
    }

    public void start() {
        if (task != null && !this.isCancelled())
            throw new CommandException("The regeneration is already started.");

        // unblock the block
        this.engine.getSqliteEngine().getBlockDAO().unblock();

        // start the task
        task = Bukkit.getScheduler().runTaskTimer(
                this.engine.getPlugin(),
                this::process,
                this.settings.getTickRegenerateInterval(),
                this.settings.getTickRegenerateInterval()
        );

        // save into configuration
        this.settings.setRegenerationActive(true);
    }

    public void stop() {
        stop(false);
    }

    public void stop(boolean force) {
        // check if task not cancel
        if (task != null && !this.isCancelled()) {

            // cancel the task
            task.cancel();

            // remove to the memory
            task = null;

            // save into configuration
            this.settings.setRegenerationActive(false);
        } else if (!force) {
            throw new CommandException("The regeneration cron is not started.");
        }
    }

    private boolean isCancelled() {
        if (MiningManager.checkVersion(1, 13))
            return task.isCancelled();
        return false;
    }

    public void process() {
        // get random world
        final String worldName = this.sqLiteEngine.getBlockDAO().randomWord(this.getWorlds());

        // no worlds means database empty
        if (worldName == null)
            return;

        // get random block from world
        final BlockData bd = this.sqLiteEngine.getBlockDAO().randomBlock(worldName);

        // no block means no block placed
        if (bd == null)
            return;

        // check if world exists
        final World world = this.engine.getServer().getWorld(worldName);
        if (world == null)
            return;

        // check if block exists and apply
        final Block block = world.getBlockAt(bd.getX(), bd.getY(), bd.getZ());
        if (block == null)
            return;

        // change the block
        if (block.getType() == Material.AIR) {
            block.setType(bd.getMaterial());
            this.engine.getSqliteEngine().getBlockDAO().delete(bd);
        }

        // change the status of the block to true
        else {
            bd.setBlocked(true);
            this.engine.getSqliteEngine().getBlockDAO().update(bd);
        }
    }

    public List<String> getWorlds() {
        final List<String> worlds = new ArrayList<>();
        for (final World world : this.engine.getServer().getWorlds()) {
            worlds.add(world.getName());
        }
        return worlds;
    }
}
