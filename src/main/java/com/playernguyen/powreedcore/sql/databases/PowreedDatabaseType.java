package com.playernguyen.powreedcore.sql.databases;

/**
 * The data type of SQL
 */
public interface PowreedDatabaseType {

    /**
     * The definition of variable. For example: <br>
     * the <i>VARCHAR</i> is a definition
     * or <i>INT</i>, either
     *
     * @return the definition of variable
     */
    String getDefinition();

    /**
     * The definition of auto-increment value because of the different from another SQL Server base.
     *
     * @return The definition of auto-increment value
     */
    String getAutoIncrement();

}
