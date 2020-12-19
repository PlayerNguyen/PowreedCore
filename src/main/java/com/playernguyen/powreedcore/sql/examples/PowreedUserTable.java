package com.playernguyen.powreedcore.sql.examples;

import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseAbstract;
import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseField;
import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseTypeMySQL;
import com.playernguyen.powreedcore.sql.tables.PowreedDatabaseTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PowreedUserTable extends PowreedDatabaseTable<PowreedUserObject, PowreedDatabaseTypeMySQL> {

    public PowreedUserTable(PowreedDatabaseAbstract database, String name) throws SQLException {
        super(database, name);
    }

    @Override
    protected void beforeInit() {
        this.addField(new PowreedDatabaseField<>("id", PowreedDatabaseTypeMySQL.INTEGER, 11, true));
        this.addField(new PowreedDatabaseField<>("uuid", PowreedDatabaseTypeMySQL.VARCHAR, 255));
        this.addField(new PowreedDatabaseField<>("balance", PowreedDatabaseTypeMySQL.VARCHAR, 255));
    }

    @Override
    public List<PowreedUserObject> fromResult(ResultSet resultSet) throws SQLException {
        List<PowreedUserObject> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(new PowreedUserObject(
                    UUID.fromString(resultSet.getString("uuid")),
                    resultSet.getDouble("balance")
            ));
        }
        return list;
    }
}
