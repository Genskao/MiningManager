package fr.tropweb.miningmanager.commands.struct;

import fr.tropweb.miningmanager.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public interface SubCommand {

    default void help(final Player player) {
        player.sendMessage(this.help().getHelp());
        for (final CommandManager commandManager : this.subCommand()) {
            player.sendMessage(commandManager.getHelp());
        }
    }

    default boolean contains(Player player, CommandManager commandManager, int index, String[] args) {
        return index < args.length && args[index].equalsIgnoreCase(commandManager.getCommand()) && Utils.hasPerm(player, commandManager);
    }

    void onCommand(Player player, String[] args);

    CommandManager help();

    CommandManager permission();

    List<CommandManager> subCommand();
}