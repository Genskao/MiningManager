package fr.tropweb.miningmanager;

import fr.tropweb.miningmanager.commands.struct.CommandManager;
import fr.tropweb.miningmanager.commands.struct.SubCommand;
import fr.tropweb.miningmanager.engine.Engine;
import fr.tropweb.miningmanager.exception.PermissionException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {
    private Engine engine;

    public CommandHandler(Engine engine) {
        this.engine = engine;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandName, @NotNull String[] args) {
        final Player player = (Player) commandSender;
        try {
            commandManager(player, command, args);
        } catch (CommandException e) {
            Utils.red(player, e.getMessage());
        } catch (PermissionException e) {
            Utils.red(player, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Utils.red(player, "Command error: " + e.getMessage());
            Utils.green(player, "Type /mm help for command information");
        }
        return true;
    }


    private void commandManager(@NotNull Player player, @NotNull Command command, @NotNull String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            help(player);
            return;
        }

        // if it's command
        else if (command.getName().equalsIgnoreCase("mm")) {
            final String subCommandName = args[0].toLowerCase();
            for (final CommandManager commandManager : this.engine.getCommands().keySet()) {
                if (commandManager.getCommand().equalsIgnoreCase(subCommandName)) {
                    onCommand(commandManager, player, args);
                    return;
                }
            }

            // inform the player
            Utils.red(player, String.format("Command %s not found", subCommandName));
        }

        // help if not found
        help(player);
    }

    public void onCommand(@NotNull CommandManager commandManager, @NotNull Player player, @NotNull String[] args) {

        // select the sub command
        final SubCommand subCommand = this.engine.getCommands().get(commandManager);

        // check if the user have the permission to use it
        if (!Utils.hasPerm(player, subCommand))
            throw new PermissionException();

        // check if the user need help
        if (args.length > 1 && args[1].equalsIgnoreCase("help")) {
            subCommand.help(player);
            return;
        }

        // start the sub command
        subCommand.onCommand(player, args);
    }

    private void help(@NotNull Player player) {
        player.sendMessage(ChatColor.DARK_RED + this.engine.getPlugin().getName() + ": " + ChatColor.DARK_GREEN + this.engine.getPlugin().getDescription().getVersion());
        player.sendMessage(ChatColor.DARK_RED + "Commands:");
        for (final CommandManager commandManager : this.engine.getCommands().keySet()) {
            this.engine.getCommands().get(commandManager).help(player);
        }
    }
}
