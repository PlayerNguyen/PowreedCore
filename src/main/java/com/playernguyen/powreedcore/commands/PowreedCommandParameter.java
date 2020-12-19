package com.playernguyen.powreedcore.commands;

/**
 * The parameter of the command.
 */
public class PowreedCommandParameter {

    private final String name;
    private final boolean required;

    public PowreedCommandParameter(String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    /**
     * @return The name of this parameter for the command
     */
    public String getName() {
        return name;
    }

    /**
     * @return Whether is required to use or an optional
     */
    public boolean isRequired() {
        return required;
    }
}
