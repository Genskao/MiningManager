package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static fr.tropweb.miningmanager.commands.struct.CommandManager.AUTO_SCAN;
import static fr.tropweb.miningmanager.commands.struct.CommandManager.SCAN;

public class Scan implements SubCommand {
    private final Engine engine;

    public Scan(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {
        final PlayerLite playerLite = this.engine.getPlayerEngine().getPlayerLite(player);

        // check if player want to stop
        if (attribute == AUTO_SCAN) {
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
        return SCAN;
    }

    @Override
    public CommandManager permission() {
        return SCAN;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList(AUTO_SCAN);
    }
}