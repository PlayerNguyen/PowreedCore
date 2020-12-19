package com.playernguyen.powreedcore.commands.subs;

import com.playernguyen.powreedcore.PowreedCore;
import com.playernguyen.powreedcore.commands.PowreedCommandParameter;
import com.playernguyen.powreedcore.commands.executors.PowreedCommandExecutor;
import com.playernguyen.powreedcore.commands.powreed.PowreedCommandResult;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PowreedCommandSubCommandPowreedTest extends PowreedCommandSubCommand<PowreedCommandResult> {

    public PowreedCommandSubCommandPowreedTest(Plugin plugin, PowreedCommandExecutor<PowreedCommandResult> mainExecutor) {
        super(plugin, "test", mainExecutor, "The test");
        getParameters().add(new PowreedCommandParameter("eval", false));
    }

    @Override
    public PowreedCommandResult command(@NotNull CommandSender sender, List<String> args) {

        // Create new thread to test
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = PowreedCore.getPlugin(PowreedCore.class).getEstablishment().openConnection()) {
                    sender.sendMessage(ChatColor.GOLD + "Table List: ");
                    try (ResultSet resultSet = connection.prepareStatement("SHOW TABLES").executeQuery()) {
                        while (resultSet.next()) {
                            String table = resultSet.getString(1);
                            sender.sendMessage(ChatColor.GRAY + " + " + ChatColor.RED + table);
                            // Show info
                            try (ResultSet tableInfo = connection.prepareStatement("DESCRIBE " + table).executeQuery()) {
                                while (tableInfo.next()) {
                                    sender.sendMessage(ChatColor.AQUA
                                            + "  - "
                                            + ChatColor.AQUA
                                            + tableInfo.getString(1)
                                            + " " + ChatColor.GRAY
                                            + tableInfo.getString(2)
                                    );
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getPlugin());

        return PowreedCommandResult.NULL;
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        return null;
    }
}
