package com.playernguyen.powreedcore.commands;

import com.playernguyen.powreedcore.commands.executors.PowreedCommandExecutor;
import com.playernguyen.powreedcore.managers.PowreedManagerSet;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.util.TreeSet;

/**
 * The executors manager to manage the execute
 */
public class PowreedCommandExecutorManager extends PowreedManagerSet<PowreedCommandExecutor<?>> {

    public PowreedCommandExecutorManager() {
        super(new TreeSet<>());
    }

    /**
     * Add new command and register it to use
     *
     * @param item the command item
     */
    public void add(PowreedCommandExecutor<?> item) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(item.getName());
        if (pluginCommand == null) {
            throw new NullPointerException(
                    "Not found plugin which registered this command. Try to put command into plugin.yml:Commands"
            );
        }
        this.collection().add(item);
        pluginCommand.setExecutor(item);
    }

}
