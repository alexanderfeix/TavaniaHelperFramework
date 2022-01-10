package net.tavania.framework.sql.mysql;

import net.tavania.framework.logger.ConsoleLogger;
import net.tavania.framework.logger.LoggerType;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySQLHelper {

    private final String hostname;
    private final String name;
    private final String username;
    private final String password;
    private final int port;
    private final Connection connection;
    private String[] columns;
    private String[] attributes;
    private String tableName;

    public MySQLHelper(String hostname, String name, String username, String password, Connection connection, int port) {
        this.hostname = hostname;
        this.name = name;
        this.username = username;
        this.password = password;
        this.connection = connection;
        this.port = port;
    }

    /**
     * Creating the SQL-Table
     *
     * @param name       is the table-name
     * @param attributes are the sql-attributes (e.g. INT, VARCHAR(100), VARCHAR(100))
     * @param columns    are the table-columns (e.g. LogId, LogDate, Message)
     */
    public void createTable(String name, String[] attributes, String[] columns) {
        this.columns = columns;
        this.tableName = name;
        this.attributes = attributes;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + getTableName() + " (" + convertAttributes(attributes) + " )");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            ConsoleLogger.getInstance().log(LoggerType.SUCCESS, "Successfully created new table!");
        } catch (SQLException e) {
            ConsoleLogger.getInstance().log(LoggerType.ERROR, MySQLHelper.class, "Could not create SQL-Table!");
            e.printStackTrace();
        }
    }

    /**
     * Checks if an entry exists
     *
     * @param column is the column in the table
     * @param value  is the searched entry
     * @return
     */
    public boolean exists(String column, int value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + column + " = ?");
            ps.setInt(1, value);
            ResultSet result = ps.executeQuery();
            boolean isExisting = result.next();
            result.close();
            ps.close();
            return isExisting;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if an entry exists
     *
     * @param column is the column in the table
     * @param value  is the searched entry
     * @return
     */
    public boolean exists(String column, String value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + column + " = ?");
            ps.setString(1, value);
            ResultSet result = ps.executeQuery();
            boolean isExisting = result.next();
            result.close();
            ps.close();
            return isExisting;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if an entry exists
     *
     * @param column is the column in the table
     * @param value  is the searched entry
     * @return
     */
    public boolean exists(String column, double value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + column + " = ?");
            ps.setDouble(1, value);
            ResultSet result = ps.executeQuery();
            boolean isExisting = result.next();
            result.close();
            ps.close();
            return isExisting;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Inserts a new entry with just one field placed
     *
     * @param columnName is the table-column where the entry should be placed
     * @param value      is the value to place
     */
    public void insert(String columnName, String value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("INSERT INTO " + getTableName() + " (" + convertArrayToString(getColumns()) + ") VALUES (" + getEmptyValuesString(getColumns().length) + ")");
            setNullValues(ps, getColumns().length);
            ps.setString(getColumn(columnName), value);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Inserts a new entry with just one field placed
     *
     * @param columnName is the table-column where the entry should be placed
     * @param value      is the value to place
     */
    public void insert(String columnName, int value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("INSERT INTO " + getTableName() + " (" + convertArrayToString(getColumns()) + ") VALUES (" + getEmptyValuesString(getColumns().length) + ")");
            setNullValues(ps, getColumns().length);
            ps.setInt(getColumn(columnName), value);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Inserts multiple entries to the table in one row
     *
     * @param values are the entries, placed in the table from left to right
     */
    public void insert(String[] values) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("INSERT INTO " + getTableName() + " (" + convertArrayToString(getColumns()) + ") VALUES (" + getEmptyValuesString(getColumns().length) + ")");
            for (int i = 0; i < values.length; i++) {
                ps.setString(getColumn(getColumns()[i]), values[i]);
            }
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Getting a string-value
     *
     * @param keyColumn   is the column in the table where the value should be searched
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @return the selected field
     */
    public String getStringValue(String keyColumn, int key, String columnLabel) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + keyColumn + " = ?");
            ps.setInt(1, key);
            ResultSet result = ps.executeQuery();
            result.next();
            String output = result.getString(columnLabel);
            result.close();
            ps.close();
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Getting a string-value
     *
     * @param keyColumn   is the column in the table where the value should be searched
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @return the selected field
     */
    public String getStringValue(String keyColumn, String key, String columnLabel) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + keyColumn + " = ?");
            ps.setString(1, key);
            ResultSet result = ps.executeQuery();
            result.next();
            String output = result.getString(columnLabel);
            result.close();
            ps.close();
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Getting an int-value
     *
     * @param keyColumn   is the column in the table where the value should be searched
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @return the selected field
     */
    public int getIntValue(String keyColumn, String key, String columnLabel) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + keyColumn + " = ?");
            ps.setString(1, key);
            ResultSet result = ps.executeQuery();
            result.next();
            int output = result.getInt(columnLabel);
            result.close();
            ps.close();
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Getting an int-value
     *
     * @param keyColumn   is the column in the table where the value should be searched
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @return the selected field
     */
    public int getIntValue(String keyColumn, int key, String columnLabel) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + keyColumn + " = ?");
            ps.setInt(1, key);
            ResultSet result = ps.executeQuery();
            result.next();
            int output = result.getInt(columnLabel);
            result.close();
            ps.close();
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Sets a value
     *
     * @param keyColumn   is the column in the table where the value should get replaced
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @param value       is the value to write in the selected field
     */
    public void setValue(String keyColumn, String key, String columnLabel, String value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + keyColumn + " = ?");
            ps.setString(1, value);
            ps.setString(2, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets a value
     *
     * @param keyColumn   is the column in the table where the value should get replaced
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @param value       is the value to write in the selected field
     */
    public void setValue(String keyColumn, String key, String columnLabel, int value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + keyColumn + " = ?");
            ps.setInt(1, value);
            ps.setString(2, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets a value
     *
     * @param keyColumn   is the column in the table where the value should get replaced
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @param value       is the value to write in the selected field
     */
    public void setValue(String keyColumn, int key, String columnLabel, int value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + keyColumn + " = ?");
            ps.setInt(1, value);
            ps.setInt(2, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets a value
     *
     * @param keyColumn   is the column in the table where the value should get replaced
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @param value       is the value to write in the selected field
     */
    public void setValue(String keyColumn, int key, String columnLabel, String value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + keyColumn + " = ?");
            ps.setString(1, value);
            ps.setInt(2, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets a value
     *
     * @param keyColumn   is the column in the table where the value should get replaced
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @param value       is the value to write in the selected field
     */
    public void setValue(String keyColumn, int key, String columnLabel, boolean value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + keyColumn + " = ?");
            ps.setBoolean(1, value);
            ps.setInt(2, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets a value
     *
     * @param keyColumn   is the column in the table where the value should get replaced
     * @param key         is the identifier in the table column
     * @param columnLabel identifies the right field in the selected row
     * @param value       is the value to write in the selected field
     */
    public void setValue(String keyColumn, String key, String columnLabel, boolean value) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + columnLabel + " = ? WHERE " + keyColumn + " = ?");
            ps.setBoolean(1, value);
            ps.setString(2, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes an entry
     *
     * @param keyColumn is the column in the table where the entry should get deleted
     * @param key       is the identifier in the table column
     */
    public void delete(String keyColumn, int key) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("DELETE FROM " + getTableName() + " WHERE " + keyColumn + " = ?");
            ps.setInt(1, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes an entry
     *
     * @param keyColumn is the column in the table where the entry should get deleted
     * @param key       is the identifier in the table column
     */
    public void delete(String keyColumn, String key) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("DELETE FROM " + getTableName() + " WHERE " + keyColumn + " = ?");
            ps.setString(1, key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Counts all rows
     *
     * @param keyColumn is the table column that should get counted
     * @return the number of rows
     */
    public int countAll(String keyColumn) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT COUNT(" + keyColumn + ") FROM " + getTableName() + "");
            ResultSet resultSet = ps.executeQuery();
            int output = 0;
            while (resultSet.next()) {
                output = resultSet.getInt(1);
            }
            resultSet.close();
            ps.close();
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets all data from the table
     *
     * @return
     */
    public ArrayList<String> getAll() {
        ArrayList<String> members = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + getTableName() + "");
            while (resultSet.next()) {
                members.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    private void setNullValues(PreparedStatement preparedStatement, int length) throws SQLException {
        for (int i = 1; i < length; i++) {
            preparedStatement.setString(i, "");
        }
    }

    private void setExistingInput(PreparedStatement preparedStatement, String[] attributes, String keyColumn, String key, String columnLabel) throws SQLException {
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].startsWith("VARCHAR")) {
                preparedStatement.setString(i, getStringValue(keyColumn, key, columnLabel));
            } else if (attributes[i].startsWith("INT")) {
                preparedStatement.setInt(i, getIntValue(keyColumn, key, columnLabel));
            } else {
                ConsoleLogger.getInstance().log(LoggerType.ERROR, "Could not read attribute " + i);
            }
        }
    }

    /**
     * Converts the array with the column-names to a string
     *
     * @param array
     * @return
     */
    private String convertArrayToString(String[] array) {
        String arrayString = "";
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                arrayString = arrayString + array[i] + ", ";
            } else {
                arrayString = arrayString + array[i];
            }
        }
        return arrayString;
    }

    /**
     * Creates the empty-value string that gets replaced in further sql statements (VALUES (?, ?, ...))
     *
     * @param length
     * @return
     */
    private String getEmptyValuesString(int length) {
        String valuesString = "";
        for (int i = 0; i < length; i++) {
            if (i < length - 1) {
                valuesString = valuesString + "?, ";
            } else {
                valuesString = valuesString + "?";
            }
        }
        return valuesString;
    }

    /**
     * Converts the attributes and column-names to a string, used in the creation of a sql-table
     *
     * @param attributes
     * @return
     */
    private String convertAttributes(String[] attributes) {
        String attributeString = "";
        for (int i = 0; i < attributes.length; i++) {
            if (i < attributes.length - 1) {
                attributeString = attributeString + (getColumns()[i] + " " + attributes[i] + ", ");
            } else {
                attributeString = attributeString + (getColumns()[i] + " " + attributes[i]);
            }
        }
        return attributeString;
    }

    /**
     * Gets the column-number by a column-name
     *
     * @param value is the table-column name
     * @return
     */
    private int getColumn(String value) {
        for (int i = 0; i < getColumns().length; i++) {
            if (getColumns()[i].equals(value)) {
                return i + 1;
            }
        }
        return 0;
    }

    private Connection getConnection() {
        return connection;
    }

    private int getPort() {
        return port;
    }

    private String getUsername() {
        return username;
    }

    private String getPassword() {
        return password;
    }

    private String getName() {
        return name;
    }

    private String getHostname() {
        return hostname;
    }

    public String[] getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getAttributes() {
        return attributes;
    }
}