package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Scan implements SubCommand {
    private final Engine engine;

    public Scan(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // check if player want to stop
        if (this.contains(player, CommandManager.AUTO_SCAN, 1, args)) {
            // set or unset the auto scan
            playerLite.setAutoScan(!playerLite.isAutoScan());
            if (playerLite.isAutoScan()) {
                Utils.green(player, "the auto scan has been activated.");
            } else {
                Utils.green(player, "the auto scan has been unactivated.");
                return;
            }
        }

        this.engine.getChunkEngine().onCommandInChunkOfPlayer(player);
    }

    @Override
    public CommandManager help() {
        return CommandManager.SCAN;
    }

    @Override
    public CommandManager permission() {
        return CommandManager.SCAN;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList(CommandManager.AUTO_SCAN);
    }
}