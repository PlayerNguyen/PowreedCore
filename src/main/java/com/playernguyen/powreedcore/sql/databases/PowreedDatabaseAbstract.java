package com.playernguyen.powreedcore.sql.databases;

import com.playernguyen.powreedcore.sql.establishments.PowreedSQLEstablishmentInterface;
import com.playernguyen.powreedcore.sql.tables.PowreedDatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to manage database
 */
public abstract class PowreedDatabaseAbstract {

    private final List<PowreedDatabaseTable<?, ?>> tables;
    private final PowreedSQLEstablishmentInterface establishment;

    public PowreedDatabaseAbstract(PowreedSQLEstablishmentInterface establishment) {
        this.tables = new ArrayList<>();
        this.establishment = establishment;
    }

    public List<PowreedDatabaseTable<?, ?>> getTables() {
        return tables;
    }

    public PowreedSQLEstablishmentInterface getEstablishment() {
        return establishment;
    }
}
