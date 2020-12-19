package com.playernguyen.powreedcore.commands.powreed;

import com.playernguyen.powreedcore.commands.PowreedCommandResultInterface;

/**
 * The primary command result of Powreed plugin
 */
public enum PowreedCommandResult implements PowreedCommandResultInterface {

    /**
     * Null object
     */
    NULL {
        @Override
        public String getMessage() {
            return null;
        }
    },
    NO_PERMISSION {
        @Override
        public String getMessage() {
            return super.getMessage() + "&cYou have no permission to do this command";
        }
    },
    MISSING_ARGUMENT {
        @Override
        public String getMessage() {
            return super.getMessage() + "&cMissing the arguments";
        }
    },
    SUB_COMMAND_NOT_FOUND {
        @Override
        public String getMessage() {
            return super.getMessage() + "&cNot found the sub command";
        }
    }
    ;

    /**
     * The message will send to command sender
     *
     * @return the message which system will send to command sender
     */
    public String getMessage() {
        return "&7[&cPowreed&7] ";
    }
}
