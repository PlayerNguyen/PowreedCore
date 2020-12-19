package com.playernguyen.powreedcore.commands.subs;

import com.playernguyen.powreedcore.commands.PowreedCommand;
import com.playernguyen.powreedcore.commands.PowreedCommandParameter;
import com.playernguyen.powreedcore.commands.PowreedCommandResultInterface;
import com.playernguyen.powreedcore.commands.executors.PowreedCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * The sub command class which will be inherited by command.
 *
 * @param <Result> the result class
 */
public abstract class PowreedCommandSubCommand<Result extends PowreedCommandResultInterface> implements PowreedCommand<Result> {

    private final Plugin plugin;
    private final PowreedCommandExecutor<Result> mainExecute;
    private final String name;
    private final List<PowreedCommandParameter> parameters = new ArrayList<>();
    private final String description;

    public PowreedCommandSubCommand(Plugin plugin,
                                    String name,
                                    PowreedCommandExecutor<Result> mainExecute,
                                    String description) {
        this.plugin = plugin;
        this.name = name;
        this.mainExecute = mainExecute;
        this.description = description;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PowreedCommandParameter> getParameters() {
        return parameters;
    }

    @Override
    public String getPermission() {
        return mainExecute.getPermission().concat(".").concat(this.name);
    }

    public PowreedCommandExecutor<Result> getMainExecute() {
        return mainExecute;
    }

    @Override
    public String toHelpString() {
        return ChatColor.GOLD + this.getMainExecute().getName()
                + " " + PowreedCommand.super.toHelpString();
    }
}
