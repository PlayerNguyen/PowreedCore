package com.playernguyen.powreedcore.commands.powreed;

import com.playernguyen.powreedcore.commands.executors.PowreedCommandExecutor;
import com.playernguyen.powreedcore.commands.subs.PowreedCommandSubCommand;
import com.playernguyen.powreedcore.commands.subs.PowreedCommandSubCommandPowreedTest;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PowreedCommandExecutorPowreed extends PowreedCommandExecutor<PowreedCommandResult> {

    public PowreedCommandExecutorPowreed(Plugin plugin) {
        super(plugin, "powreed", "The Powreed plugin' command");
        // Register sub command here
        this.addSubCommand(new PowreedCommandSubCommandPowreedTest(plugin, this));
    }

    @Override
    public PowreedCommandResult command(@NotNull CommandSender sender, List<String> args) {
        // Check permissions to sender
        if (!sender.hasPermission(this.getPermission()) || !sender.isOp()) {
            return PowreedCommandResult.NO_PERMISSION;
        }

        // Check arguments
        if (args.size() == 0) {
            for (PowreedCommandSubCommand<PowreedCommandResult> subCommand : this.getSubCommands()) {
                sender.sendMessage(subCommand.toHelpString());
            }
            return PowreedCommandResult.MISSING_ARGUMENT;
        }

        // Search for the sub-command
        PowreedCommandSubCommand<PowreedCommandResult> subCommand = this.getSubCommands()
                .stream().filter(e -> e.getName().equals(args.get(0))).findAny().orElse(null);
        // Whether null
        if (subCommand == null) {
            return PowreedCommandResult.SUB_COMMAND_NOT_FOUND;
        }
        // Return the next index
        return subCommand.command(sender, args.subList(0, args.size() - 1));
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        // System.out.println("Argument size " + args.size());
        if (args.size() > 1) {
            // Find command
            PowreedCommandSubCommand<PowreedCommandResult> subCommand = getSubCommands().stream()
                    .filter(e -> e.getName().equals(args.get(0))).findAny().orElse(null);
            return (subCommand != null) ? subCommand.tab(sender, args.subList(0, args.size() - 1)) : null;
        }
        return this.getSubCommands()
                .stream()
                .sorted(Comparator.comparing(PowreedCommandSubCommand::getName))
                .map(PowreedCommandSubCommand::getName)
                .collect(Collectors.toList());
    }
}
