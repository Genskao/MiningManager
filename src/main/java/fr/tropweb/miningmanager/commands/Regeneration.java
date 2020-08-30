package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static fr.tropweb.miningmanager.commands.struct.CommandManager.REGENERATION;
import static fr.tropweb.miningmanager.commands.struct.CommandManager.REGENERATION_STOP;

public class Regeneration implements SubCommand {
    private final Engine engine;

    public Regeneration(final Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {

        // check if the player want to stop the regeneration
        if (attribute == REGENERATION_STOP) {

            // stop regeneration
            this.engine.getRegenerationEngine().stop();

            // inform player
            Utils.green(player, "Regeneration has been stopped.");

            // end
            return;
        }

        // start regeneration
        this.engine.getRegenerationEngine().start();

        // inform player
        Utils.green(player, "Regeneration has been started.");
    }

    @Override
    public CommandManager help() {
        return REGENERATION;
    }

    @Override
    public CommandManager permission() {
        return REGENERATION;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList(REGENERATION_STOP);
    }
}
