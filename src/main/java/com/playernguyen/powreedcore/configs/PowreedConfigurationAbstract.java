package com.playernguyen.powreedcore.configs;

import com.google.common.base.Preconditions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * An abstract class which will represent for another
 * configuration class
 *
 * @param <T> the {@link PowreedConfigurationEntryInterface} to refer
 * @see YamlConfiguration
 * @see FileConfiguration
 */
public abstract class PowreedConfigurationAbstract<T extends PowreedConfigurationEntryInterface> {

    private final File file;
    private FileConfiguration fileConfiguration;

    /**
     * The main construction of Configuration
     *
     * @param plugin    the main plugin of this configuration
     * @param entryList the entry list to declare
     * @param fileName  the file name of this config
     * @param parent    the parent folder name of this config
     */
    public PowreedConfigurationAbstract(@NotNull Plugin plugin,
                                        T[] entryList,
                                        @NotNull String fileName,
                                        @Nullable String parent) throws IOException {
        // Not null checking
        Preconditions.checkNotNull(plugin);
        Preconditions.checkNotNull(fileName);
        // Set up the data folder first
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            throw new IllegalStateException(String.format("Cannot initial the data folder of plugin %s",
                    plugin.getDescription().getName()));
        }
        File parentFolder = (parent == null)
                ? dataFolder
                : new File(dataFolder, parent);
        if (!parentFolder.exists() && !parentFolder.mkdir()) {
            throw new IllegalStateException("Cannot initial the parent folder of configuration.");
        }
        // Now, setup the file
        this.file = new File(parentFolder, fileName);
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        this.fileConfiguration.options().copyDefaults(true);
        // Load default value
        for (T entry : entryList) {
            this.fileConfiguration.addDefault(entry.path(), entry.initial());
        }
        // Call init
        init();
        // Save
        this.save();
    }

    /**
     * Save the configuration as YAML loader
     *
     * @throws IOException whenever configuration class cannot be saved
     * @see FileConfiguration#save(File)
     */
    public void save() throws IOException {
        this.fileConfiguration.save(this.file);
    }

    /**
     * Initialize method of the configuration. This method will be called after loading file configuration
     */
    protected abstract void init();

    /**
     * Get an object which was configured by an entry class
     *
     * @param entry the configuration entry
     * @return the item which was configured
     */
    public Object getObject(T entry) {
        return this.fileConfiguration.get(entry.path());
    }

    /**
     * Casting {@link #get(PowreedConfigurationEntryInterface)} as String
     *
     * @param entry the configuration entry
     * @return the String value of method {@link #get(PowreedConfigurationEntryInterface)}
     * @throws ClassCastException whether the entry wasn't a String
     */
    public String getString(T entry) {
        return (String) get(entry);
    }

    /**
     * Casting {@link #get(PowreedConfigurationEntryInterface)} as Integer
     * @param entry the configuration entry
     * @return the Integer value of method {@link #get(PowreedConfigurationEntryInterface)}
     * @throws ClassCastException whether the entry wasn't an Integer
     */
    public int getInt(T entry) {
        return (Integer) get(entry);
    }

    /**
     * Casting {@link #get(PowreedConfigurationEntryInterface)} as Double
     * @param entry the configuration entry
     * @return the Double value of method
     */
    public double getDouble(T entry) {
        return (Double) get(entry);
    }

    /**
     * Get an entry of configuration
     *
     * @param entry the configuration entry
     * @param <TType> a type of object
     * @return an object which will cast as TType
     * @throws ClassCastException the object was cast to wrong type
     */
    @SuppressWarnings("unchecked")
    public <TType> TType get(T entry) {
        return (TType) this.fileConfiguration.get(entry.path());
    }

    /**
     * Reload the cache configuration.
     */
    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }
}
