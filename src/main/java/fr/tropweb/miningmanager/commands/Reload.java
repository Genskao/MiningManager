package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Reload implements SubCommand {
    private final Engine engine;

    public Reload(final Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(final Player player, final CommandManager attribute) {
        this.engine.reload(player);
    }


    @Override
    public CommandManager getCommandManager() {
        return CommandManager.RELOAD;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList();
    }
}
