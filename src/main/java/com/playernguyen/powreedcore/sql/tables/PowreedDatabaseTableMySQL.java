package com.playernguyen.powreedcore.sql.tables;

import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseAbstract;
import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseTypeMySQL;

import java.sql.SQLException;

/**
 * Represent class of MySQL
 *
 * @param <T> the data type will return to user when return a {@link java.sql.ResultSet}
 */
public abstract class PowreedDatabaseTableMySQL<T> extends PowreedDatabaseTable<T, PowreedDatabaseTypeMySQL> {

    public PowreedDatabaseTableMySQL(PowreedDatabaseAbstract database, String name) throws SQLException {
        super(database, name);
    }
}
