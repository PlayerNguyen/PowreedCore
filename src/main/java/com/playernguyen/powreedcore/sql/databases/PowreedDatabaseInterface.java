package com.playernguyen.powreedcore.sql.databases;

import com.playernguyen.powreedcore.sql.establishments.PowreedSQLEstablishmentInterface;

public interface PowreedDatabaseInterface {

    /**
     * The name of that database
     * @return name of database
     */
    String getName();

    /**
     * The establishment class of SQL
     * @return the establishment class
     */
    PowreedSQLEstablishmentInterface getEstablishment();

}
