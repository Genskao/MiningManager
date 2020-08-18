package fr.tropweb.miningmanager.commands;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Regenerate implements SubCommand {
    private final Engine engine;

    public Regenerate(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        throw new CommandException("Not implemented");
    }

    @Override
    public CommandManager help() {
        return CommandManager.REGENERATE;
    }

    @Override
    public CommandManager permission() {
        return CommandManager.REGENERATE;
    }

    @Override
    public List<CommandManager> subCommand() {
        return Arrays.asList();
    }
}