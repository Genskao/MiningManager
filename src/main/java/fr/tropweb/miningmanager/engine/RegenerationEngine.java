package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.pojo.BlockLite;
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
        if (task == null) {
            // unblock the block
            this.engine.getSqliteEngine().getBlockDAO().unblock();

            // start the task
            task = Bukkit.getScheduler().runTaskTimer(this.engine.getPlugin(), this::process, this.settings.getTickRegenerateInterval(), this.settings.getTickRegenerateInterval());
        } else {
            throw new CommandException("The regeneration is already started.");
        }
    }

    public void stop() {
        stop(false);
    }

    public void stop(boolean force) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
            task = null;
        } else if (!force) {
            throw new CommandException("The regeneration cron is not started.");
        }
    }

    public void process() {
        // get random world
        final String worldName = this.sqLiteEngine.getBlockDAO().randomWord(this.getWorlds());

        // no worlds means database empty
        if (worldName == null)
            return;

        // get random block from world
        final BlockLite bl = this.sqLiteEngine.getBlockDAO().randomBlock(worldName);

        // no block means no block placed
        if (bl == null)
            return;

        // check if world exists
        final World world = this.engine.getServer().getWorld(worldName);
        if (world == null)
            return;

        // check if block exists and apply
        final Block block = world.getBlockAt(bl.getX(), bl.getY(), bl.getZ());
        if (block == null)
            return;

        // change the block
        if (block.getType() == Material.AIR) {
            block.setType(bl.getMaterial());
            this.engine.getSqliteEngine().getBlockDAO().delete(bl);
        }

        // change the status of the block to true
        else {
            bl.setBlocked(true);
            this.engine.getSqliteEngine().getBlockDAO().update(bl);
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