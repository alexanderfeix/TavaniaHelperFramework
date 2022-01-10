package net.tavania.framework.sql.mysql;

import net.tavania.framework.logger.ConsoleLogger;
import net.tavania.framework.logger.LoggerType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySQLDatabase {

    private final String hostname;
    private final String name;
    private final String username;
    private final String password;
    private final int port;
    private Connection connection;
    private MySQLHelper mySQLHelper;

    public MySQLDatabase(String hostname, String username, String name, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.name = name;
        this.password = password;
        this.port = port;
        connect();
        setMySQLHelper(new MySQLHelper(hostname, username, name, password, getConnection(), port));
    }

    public MySQLHelper getMySQLHelper() {
        return mySQLHelper;
    }

    private void setMySQLHelper(MySQLHelper mySQLHelper) {
        this.mySQLHelper = mySQLHelper;
    }

    /**
     * Connecting to the database when database is initialized
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + getHostname() + ":" + getPort() + "/" + getName() + "?autoReconnect=true", getUsername(), getPassword());
            ConsoleLogger.getInstance().log(LoggerType.SUCCESS, "Successfully connected to database!");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().log(LoggerType.ERROR, "Could not connect to database!");
            e.printStackTrace();
        }
    }

    public String getHostname() {
        return hostname;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }
}
