package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Regeneration implements SubCommand {
    private final Engine engine;

    public Regeneration(final Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {

        // check if the player want to stop the regeneration
        if (attribute == CommandManager.REGENERATION_STOP) {

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
    public CommandManager getCommandManager() {
        return CommandManager.REGENERATION;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList(CommandManager.REGENERATION_STOP);
    }
}
