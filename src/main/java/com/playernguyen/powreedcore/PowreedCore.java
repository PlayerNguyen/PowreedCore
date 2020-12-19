package com.playernguyen.powreedcore;

import com.playernguyen.powreedcore.commands.PowreedCommandExecutorManager;
import com.playernguyen.powreedcore.commands.powreed.PowreedCommandExecutorPowreed;
import com.playernguyen.powreedcore.settings.PowreedConfigurationSetting;
import com.playernguyen.powreedcore.settings.PowreedConfigurationSettingEntry;
import com.playernguyen.powreedcore.sql.establishments.PowreedMySQLEstablishmentAbstract;
import com.playernguyen.powreedcore.sql.establishments.PowreedSQLEstablishmentInterface;
import com.playernguyen.powreedcore.sql.examples.PowreedDatabase;
import com.playernguyen.powreedcore.sql.examples.PowreedUserObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Main class of PowreedCore plugin
 */
public final class PowreedCore extends JavaPlugin {

    private PowreedConfigurationSetting settingConfiguration;
    private PowreedSQLEstablishmentInterface establishment;
    private PowreedCommandExecutorManager executorManager;
    private PowreedDatabase database;

    /**
     * Enable the plugin code. Inherit from {@link JavaPlugin}
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            initial();
            setupSettings();
            setupSQL();
            setupCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup the command
     */
    private void setupCommands() {
        if (this.executorManager == null) {
            this.executorManager = new PowreedCommandExecutorManager();
        }
        // Register
        this.getExecutorManager().add(new PowreedCommandExecutorPowreed(this));
    }

    /**
     * Setup the SQL
     *
     * @throws SQLException           whether cannot create or connect anything with SQL Server
     * @throws ClassNotFoundException SQL driver not found
     */
    private void setupSQL() throws SQLException, ClassNotFoundException {
        // Establishment test whether player turn on the developer mode
        if (settingConfiguration.get(PowreedConfigurationSettingEntry.DEVELOPER_MODE)) {
            if (establishment == null) {
                this.establishment = new PowreedMySQLEstablishmentAbstract(
                        settingConfiguration.get(PowreedConfigurationSettingEntry.TEST_MYSQL_HOST),
                        settingConfiguration.get(PowreedConfigurationSettingEntry.TEST_MYSQL_PORT),
                        settingConfiguration.get(PowreedConfigurationSettingEntry.TEST_MYSQL_USERNAME),
                        settingConfiguration.get(PowreedConfigurationSettingEntry.TEST_MYSQL_PASSWORD),
                        settingConfiguration.get(PowreedConfigurationSettingEntry.TEST_MYSQL_DATABASE),
                        settingConfiguration.get(PowreedConfigurationSettingEntry.TEST_MYSQL_URL_PARAMETER)
                ) {
                    @Override
                    public Connection openConnection() throws SQLException {
                        return super.openConnection();
                    }
                };
            }
            // Test the database
            this.database = new PowreedDatabase(this.establishment);
            database.getUserTable().createIfNotExist("id");
            // insert test
//            this.database.getUserTable().insert(
//                    Pair.newInstance("uuid", UUID.randomUUID().toString()),
//                    Pair.newInstance("balance", "125")
//            );
            // select
            List<PowreedUserObject> powreedUserObjects =
                    database.getUserTable().selectAll("WHERE balance=?", "125");
            System.out.println(powreedUserObjects);

        }
    }

    /**
     * Disable the plugin code. Inherit from {@link JavaPlugin}
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Setting & configuration setup
     */
    private void setupSettings() throws IOException {
        if (this.settingConfiguration == null) {
            this.settingConfiguration = new PowreedConfigurationSetting(this);
        } else {
            this.settingConfiguration.reload();
        }
    }

    private void initial() {
        this.getLogger().info("Powreed Core - The API from massive");
    }

    /**
     * @return The setting configuration
     */
    public PowreedConfigurationSetting getSettingConfiguration() {
        return settingConfiguration;
    }

    /**
     * @return The executor manager of PowreedCore
     */
    public PowreedCommandExecutorManager getExecutorManager() {
        return executorManager;
    }

    /**
     * The SQL Establishment
     *
     * @return the SQL Establishment class
     */
    public PowreedSQLEstablishmentInterface getEstablishment() {
        return establishment;

    }

    /**
     * The database initiate
     * @return the database clazz
     */
    public PowreedDatabase getDatabase() {
        return database;
    }
}
