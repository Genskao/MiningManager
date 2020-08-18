package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Reload implements SubCommand {
    private final Engine engine;

    public Reload(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        this.engine.reload(player, false);
    }

    @Override
    public CommandManager help() {
        return CommandManager.RELOAD;
    }

    @Override
    public CommandManager permission() {
        return CommandManager.RELOAD;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList();
    }
}
