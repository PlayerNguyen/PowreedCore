package com.playernguyen.powreedcore.sql.establishments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class of establishment by MySQL
 */
public abstract class PowreedMySQLEstablishmentAbstract implements PowreedSQLEstablishmentInterface {

    private static final String URL = "jdbc:mysql://%s:%s/%s?%s";

    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String database;
    private final String urlParameter;

    /**
     * The constructor of the establishment class.
     *
     * @param host         the host of MySQL
     * @param port         the port of MySQL
     * @param username     the username of MySQL
     * @param password     the password of MySQL
     * @param database     the database of MySQL
     * @param urlParameter the url parameter
     * @throws ClassNotFoundException throw when system was not found the compatible Driver for MySQL
     * @throws SQLException           throw when system was not opened the connection
     * @see #openConnection()
     */
    public PowreedMySQLEstablishmentAbstract(String host,
                                             String port,
                                             String username,
                                             String password,
                                             String database,
                                             String urlParameter)
            throws ClassNotFoundException, SQLException {

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.urlParameter = urlParameter;
        // Generify the class
        Class.forName("com.mysql.jdbc.Driver");
        // Open a connection to test
        openConnection();
    }

    /**
     * A inheritance from {@link PowreedSQLEstablishmentInterface} class
     *
     * @return an opened connection to the MySQL Server
     * @throws SQLException whether system could not open the connection to MySQL Server
     * @see DriverManager#getConnection(String)
     */
    @Override
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(String.format(URL,
                host,
                port,
                database,
                urlParameter),
                username,
                password);
    }
}
