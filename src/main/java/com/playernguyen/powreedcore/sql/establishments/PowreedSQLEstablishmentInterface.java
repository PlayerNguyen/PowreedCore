package com.playernguyen.powreedcore.sql.establishments;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The establishment class will create a connection to user
 */
public interface PowreedSQLEstablishmentInterface {

    /**
     * The connection will open to do the SQL task.
     *
     * @return the opened connection
     */
    Connection openConnection() throws SQLException;

}
