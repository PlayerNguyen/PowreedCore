package com.playernguyen.powreedcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

/**
 * This class represent the command object
 */
public interface PowreedCommand<Result extends PowreedCommandResultInterface> extends Comparable<PowreedCommand<Result>> {

    /**
     * The name of the command
     *
     * @return name of the command or sub-command
     */
    String getName();

    /**
     * The parameter list of command
     *
     * @return parameter list of command
     */
    List<PowreedCommandParameter> getParameters();

    /**
     * The description to describe the command
     *
     * @return description of command
     */
    String getDescription();

    /**
     * The permission which allow player to access to the command
     *
     * @return the permission as Strings
     */
    String getPermission();

    /**
     * The plugin of the command as an instance
     *
     * @return plugin of the command
     */
    Plugin getPlugin();

    /**
     * Convert to the help string by using help command
     *
     * @return the help string by using help command
     */
    default String toHelpString() {
        // Builder
        StringBuilder builder = new StringBuilder();
        // Name
        builder.append(ChatColor.RED).append(getName()).append(" ");
        // Parameter
        Iterator<PowreedCommandParameter> iterator = this.getParameters().iterator();
        while (iterator.hasNext()) {
            PowreedCommandParameter next = iterator.next();

            if (next.isRequired()) {
                builder.append(ChatColor.GOLD).append("<").append(next.getName()).append(">");
            } else {
                builder.append(ChatColor.DARK_GRAY).append("[").append(next.getName()).append("]");
            }

            if (iterator.hasNext()) {
                builder.append(" ");
            }
        }
        // Description
        builder.append(ChatColor.RED).append(": ").append(ChatColor.GRAY).append(getDescription());
        // Return to string
        return builder.toString();
    }

    /**
     * This method will be called by using
     *
     * @param sender the sender who call command
     * @param args   the argument list
     * @return the result of command
     */
    Result command(@NotNull CommandSender sender, List<String> args);

    /**
     * Compare by the name. To order the command or something else relate to it
     *
     * @param o an another object to compare
     * @return the index of comparison related to String compare
     * @see String#compareTo(String)
     */
    @Override
    default int compareTo(@NotNull PowreedCommand o) {
        return this.getName().compareTo(o.getName());
    }

    /**
     * Tab executor of the command as suggestion
     *
     * @param sender the sender who tab
     * @param args   the previous arguments as list
     * @return the list of suggestion
     */
    List<String> tab(CommandSender sender, List<String> args);
}
