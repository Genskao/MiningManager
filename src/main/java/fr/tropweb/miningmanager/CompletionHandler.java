package fr.tropweb.miningmanager;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CompletionHandler implements TabCompleter {
    private final Engine engine;

    public CompletionHandler(Engine engine) {
        this.engine = engine;
    }

    private static List<String> clear(List<CommandManager> list, String word) {
        final List<String> results = new ArrayList();
        results.add("help");
        for (final CommandManager result : list) {
            if (result.getCommand().contains(word)) {
                results.add(result.getCommand());
            }
        }
        return results;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mm")) {
            final Player player = (Player) sender;
            if (args.length > 1) {
                return onTabComplete(args);
            } else {
                final List<String> results = new ArrayList();
                results.add("help");
                for (final CommandManager commandManager : this.engine.getCommands().keySet()) {
                    if (Utils.hasPerm(player, this.engine.getCommands().get(commandManager))) {
                        results.add(commandManager.getCommand());
                    }
                }
                return results;
            }

        }
        return new ArrayList<>();
    }

    public List<String> onTabComplete(String[] args) {
        if (args.length <= 2) {
            for (CommandManager commandManager : this.engine.getCommands().keySet()) {
                if (args[0].equalsIgnoreCase(commandManager.getCommand())) {
                    final SubCommand subCommand = this.engine.getCommands().get(commandManager);
                    if (subCommand != null)
                        return clear(subCommand.subCommand(), args[1]);
                }
            }
        }
        return new ArrayList<>();
    }
}