package com.playernguyen.powreedcore.commands.executors;

import com.playernguyen.powreedcore.commands.PowreedCommand;
import com.playernguyen.powreedcore.commands.PowreedCommandParameter;
import com.playernguyen.powreedcore.commands.PowreedCommandResultInterface;
import com.playernguyen.powreedcore.commands.powreed.PowreedCommandResult;
import com.playernguyen.powreedcore.commands.subs.PowreedCommandSubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The executor class to handle the main command
 */
public abstract class PowreedCommandExecutor<Result extends PowreedCommandResultInterface>
        implements PowreedCommand<Result>, TabExecutor {

    private final Plugin plugin;
    private final String name;
    private final List<PowreedCommandParameter> parameters;
    private final String description;
    private final Collection<PowreedCommandSubCommand<Result>> subCommands = new TreeSet<>();

    public PowreedCommandExecutor(Plugin plugin,
                                  String name,
                                  String description) {
        this.plugin = plugin;
        this.name = name;
        this.parameters = new ArrayList<>();
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getPermission() {
        return plugin.getName().toLowerCase() + ".command." + getName();
    }

    @Override
    public List<PowreedCommandParameter> getParameters() {
        return parameters;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Add new sub command into the data container as a Set
     *
     * @param subCommand a sub command to add
     */
    public void addSubCommand(PowreedCommandSubCommand<Result> subCommand) {
        this.subCommands.add(subCommand);
    }

    /**
     * Get a sub command list
     *
     * @return a sub command list
     */
    public Collection<PowreedCommandSubCommand<Result>> getSubCommands() {
        return subCommands;
    }

    /**
     * The inheritance method from {@link TabExecutor#onCommand(CommandSender, Command, String, String[])}
     *
     * @see TabExecutor#onCommand(CommandSender, Command, String, String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Catch the command
        Result result = command(sender, Arrays.asList(args));
        if (result == null) {
            return true;
        } this.catchCommand(sender, result);
        // Return true for seeing nothing
        return true;
    }

    /**
     * The method to handle the command result
     *
     * @param sender the command sender of that command
     * @param result the result of that command
     */
    public void catchCommand(CommandSender sender, PowreedCommandResultInterface result) {
        // Default, catch and send the message as super
        String message = result.getMessage();
        if (message != null && !message.equals("")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    /**
     * The inheritance of tab-executor class
     *
     * @see TabExecutor#onTabComplete(CommandSender, Command, String, String[])
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return this.tab(sender, Arrays.asList(args));
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        // Argument
        if (args.size() > 1) {
            // Find command
            PowreedCommandSubCommand<Result> subCommand = getSubCommands().stream()
                    .filter(e -> e.getName().equals(args.get(0))).findAny().orElse(null);
            return (subCommand != null) ? subCommand.tab(sender, args.subList(0, args.size() - 1)) : null;
        }
        // Find result
        return this.getSubCommands()
                .stream()
                .sorted(Comparator.comparing(PowreedCommandSubCommand::getName))
                .map(PowreedCommandSubCommand::getName)
                .collect(Collectors.toList());
    }
}
