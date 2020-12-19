package com.playernguyen.powreedcore.configs;

/**
 * An interface class represent to each configuration data.
 * This class should be an Enum for easy to get-and-use.
 */
public interface PowreedConfigurationEntryInterface {

    /**
     * The path of the configuration entry
     *
     * @return the path of entry
     */
    String path();

    /**
     * This value will be declared as the first time plugin load (default plugin).
     *
     * @return the default value will be declared as the first time
     */
    Object initial();

}
