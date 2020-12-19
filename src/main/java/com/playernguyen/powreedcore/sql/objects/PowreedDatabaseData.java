package com.playernguyen.powreedcore.sql.objects;

import java.sql.ResultSet;

/**
 * The supply class to parse as data
 */
public interface PowreedDatabaseData {


    PowreedDatabaseData supply(ResultSet resultSet);

}
