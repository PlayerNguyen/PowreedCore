package com.playernguyen.powreedcore.sql.tables;

import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseAbstract;
import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseField;
import com.playernguyen.powreedcore.sql.databases.PowreedDatabaseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This abstract class represent table for SQL.
 *
 * @param <T> the represent object to return the value for it.
 * @param <J> the data type of field compatible with SQL type you are using.
 */
public abstract class PowreedDatabaseTable<T, J extends PowreedDatabaseType> {

    private final PowreedDatabaseAbstract database;
    private final String name;
    private final List<PowreedDatabaseField<J>> fields = new ArrayList<>();

    public PowreedDatabaseTable(PowreedDatabaseAbstract database, String name) throws SQLException {
        this.database = database;
        this.name = name;
        // Load the before init
        beforeInit();
        // Create the table
        this.createIfNotExist("id");
    }

    public boolean addField(PowreedDatabaseField<J> type) {
        if (hasField(type.getName())) {
            throw new IllegalStateException(String.format("The field %s was declared before", type.getName()));
        }
        return fields.add(type);
    }

    /**
     * Create the table whether not exist
     *
     * @param primaryKey the primary key field. Whether null, throw new NullPointerException
     * @throws SQLException cannot establish connection or execute query
     */
    public void createIfNotExist(String primaryKey) throws SQLException {
        // Checking the fields
        if (fields.size() <= 0) {
            throw new IllegalStateException("The field cannot be empty");
        }
        // Then build & execute the query
        Connection connection = this.database.getEstablishment().openConnection();
        StringBuilder fieldBuilder = new StringBuilder();
        for (PowreedDatabaseField<J> field : fields) {
            // Put the query
            fieldBuilder.append(field.toQuery());
            // Put the comma
            if (!fields.get(fields.size() - 1).equals(field)) {
                fieldBuilder.append(", ");
            }
        }
        // Primary key
        fieldBuilder.append(String.format(", PRIMARY KEY (%s)", primaryKey));
        // Build the query
        String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", name, fieldBuilder.toString());
        // Say that we are creating an evolution bla bla
        connection.prepareStatement(query).executeUpdate();
    }

    /**
     * Select with the target field.
     *
     * @param target      target list which separator by a comma (,) symbol
     * @param params      a custom parameter of this query
     * @param preparation a preparation for that query
     * @return {@link #fromResult(ResultSet)} which process the {@link ResultSet} data
     * @throws SQLException cannot establish connection or execute query.
     */
    public List<T> select(String target, String params, String... preparation) throws SQLException {
        // Preparing for the connection
        Connection connection = this.database.getEstablishment().openConnection();
        PreparedStatement statement =
                connection.prepareStatement(String.format("SELECT %s FROM %s %s", target, name, params));

        // Prepared Statement
        for (int i = 0; i < preparation.length; i++) {
            statement.setObject(i + 1, preparation[i]);
        }

        // Creating a list and returning
        ResultSet resultSet = statement.executeQuery();
        return fromResult(resultSet);
    }

    /**
     * Select all fields (non-target).
     *
     * @param params      a custom parameter of this query
     * @param preparation a preparation for that query
     * @return {@link #fromResult(ResultSet)} which process the {@link ResultSet} data
     * @throws SQLException cannot establish connection or execute query.
     * @see #select(String, String, String...)
     */
    public List<T> selectAll(String params, String... preparation) throws SQLException {
        return this.select("*", params, preparation);
    }

    /**
     * Check whether has the field or not
     *
     * @param name name of the field to check
     * @return whether the field is exist, return true, or false otherwise.
     */
    public boolean hasField(String name) {
        return fields.stream().anyMatch(e -> e.getName().equals(name));
    }

    /**
     * Insert new column into table. This method will use Map parameter as pair to generate query from fields.
     * With the auto-increment field or something similar, maybe causing exception
     * <br>
     * For example: <br>
     * <ul>
     *     <li>username -> notch</li>
     *     <li>uuid -> 069a79f4-44e9-4726-a5be-fca90e38aaf5</li>
     * </ul>
     *
     * @param map the map of value
     * @return whether the column was inserted or not
     * @throws SQLException cannot establish connection or execute query.
     */
    public boolean insert(Map<String, Object> map) throws SQLException {
        Connection connection = this.database.getEstablishment().openConnection();
        // Separate the data
        StringBuilder keyQuery = new StringBuilder();
        StringBuilder objectQuery = new StringBuilder();
        List<Object> objects = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        // Handle data. This step is safe-step
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            // Field checking
            if (!hasField(next.getKey())) {
                throw new NullPointerException("Field not found " + next.getKey());
            }
            // Then append
            keyQuery.append(next.getKey());
            objectQuery.append("?");
            objects.add(next.getValue());

            if (iterator.hasNext()) {
                keyQuery.append(", ");
                objectQuery.append(", ");
            }
        }
        // Create a raw query without variables
        String rawQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                name,
                keyQuery.toString(),
                objectQuery.toString()
        );

        // Put variables inside
        PreparedStatement preparedStatement = connection.prepareStatement(rawQuery);
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            preparedStatement.setObject(i + 1, object);
        }
        // Then execute
        return preparedStatement.executeUpdate() == 1;
    }

    @SafeVarargs
    public final boolean insert(PowreedDatabaseTable.Pair<String, Object>... pairs) throws SQLException {
        // Parse to map
        Map<String, Object> map = new HashMap<>();
        for (Pair<String, Object> pair : pairs) {
            map.put(pair.getKey(), pair.getValue());
        }
        return insert(map);
    }

    /**
     * Prepare a new query by using {@link Connection#prepareStatement(String)}
     *
     * @param query  the query to prepare
     * @param params the parameters to insert into prepare
     * @return the prepared statement
     * @throws SQLException whether not connect or create query from SQL server
     */
    public PreparedStatement prepare(String query, Object... params) throws SQLException {
        Connection connection = this.database.getEstablishment().openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Insert params
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }

        // Return the statement
        return preparedStatement;
    }

    /**
     * The Pair object
     *
     * @param <K> first value class
     * @param <V> second value class
     */
    public static class Pair<K, V> {
        private final K key;
        private final V value;

        /**
         * Pair constructor to create new pair
         *
         * @param key   the key value
         * @param value the value value
         */
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return The key of class which was initiated
         */
        public K getKey() {
            return key;
        }

        /**
         * @return The value of class which was initiated
         */
        public V getValue() {
            return value;
        }

        /**
         * Create new value as static method
         *
         * @param key   the key
         * @param value the value
         * @param <K>   the generic class of key
         * @param <V>   the generic class of value
         * @return new instance of pair
         */
        public static <K, V> Pair<K, V> newInstance(K key, V value) {
            return new Pair<>(key, value);
        }
    }

    /**
     * This method will be called before init (create table).
     * Use this method to pre-process fields in table.
     */
    protected abstract void beforeInit();

    /**
     * Return a list as result from ResultSet when execute query
     *
     * @param resultSet the result set to convert
     * @return a list of result object
     * @throws SQLException cannot establish or execute the SQL
     */
    public abstract List<T> fromResult(ResultSet resultSet) throws SQLException;

}