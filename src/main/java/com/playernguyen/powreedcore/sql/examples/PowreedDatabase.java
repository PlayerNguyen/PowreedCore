package com.playernguyen.powreedcore.sql.examples;

import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseAbstract;
import com.playernguyen.powreedcore.sql.establishments.PowreedSQLEstablishmentInterface;
import com.playernguyen.powreedcore.sql.tables.PowreedDatabaseTable;

import java.sql.SQLException;

public class PowreedDatabase extends PowreedDatabaseAbstract {

    private final PowreedDatabaseTable<PowreedUserObject, ?> userTable;

    public PowreedDatabase(PowreedSQLEstablishmentInterface establishment) throws SQLException {
        super(establishment);
        this.userTable = new PowreedUserTable(this, "user");
        this.getTables().add(userTable);
    }

    public PowreedDatabaseTable<PowreedUserObject, ?> getUserTable() {
        return userTable;
    }
}
