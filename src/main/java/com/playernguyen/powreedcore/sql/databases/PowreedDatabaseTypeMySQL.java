package com.playernguyen.powreedcore.sql.databases;

/**
 * The type of MySQL Server
 */
public enum PowreedDatabaseTypeMySQL implements PowreedDatabaseType {

    VARCHAR ("VARCHAR"),
    INTEGER("INT"),


    ;

    private final String definition;

    PowreedDatabaseTypeMySQL(String definition) {
        this.definition = definition;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public String getAutoIncrement() {
        return "AUTO_INCREMENT";
    }
}
