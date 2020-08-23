package fr.tropweb.miningmanager.commands.struct;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.exception.PermissionException;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

import java.util.List;

public interface SubCommand {

    default void help(final Player player) {
        // send the first help
        player.sendMessage(this.help().getHelp());

        // read all sub command
        for (final CommandManager commandManager : this.subCommand()) {

            // check if the player have access and send help
            if (Utils.hasPerm(player, commandManager)) {
                player.sendMessage(commandManager.getHelp());
            }
        }
    }

    default CommandManager getAttribute(final String sub) {
        for (final CommandManager commandManager : this.subCommand()) {
            if (commandManager.getCommand().equalsIgnoreCase(sub)) {
                return commandManager;
            }
        }
        throw new CommandException(String.format("Attribute %s for the command does not exist.", sub));
    }

    default void onCommand(Player player, String[] args) {

        // count the number of arguments you have
        final int index = args.length;

        // you should never have more than 2
        if (index > 2) {
            throw new CommandException("You have too much arguments.");
        }

        // create the attribute
        CommandManager attribute = null;

        // if you have a sub command
        if (index == 2) {

            // get the attribute
            attribute = this.getAttribute(args[index - 1]);

            // check if the player is allowed to use it
            if (!Utils.hasPerm(player, attribute)) {
                throw new PermissionException();
            }
        }

        // start command
        this.onCommand(player, attribute);
    }

    void onCommand(Player player, CommandManager attribute);

    CommandManager help();

    CommandManager permission();

    List<CommandManager> subCommand();
}