package net.tavania.framework.logger;

import net.tavania.framework.sql.mysql.MySQLDatabase;

import java.util.Date;
import java.util.GregorianCalendar;

/*

    Copyright © 2022 Alexander Feix
    Mail: alexander.feix03@gmail.com
    Location: TavaniaHelperFramework/net.tavania.framework.logger
    Date: 10.01.2022
    
*/
public class MySQLLogger {

    private static final MySQLLogger instance = new MySQLLogger();
    private static MySQLDatabase mySQLDatabase;

    public static MySQLLogger getInstance() {
        return instance;
    }

    private static MySQLDatabase getMySQLDatabase() {
        return mySQLDatabase;
    }

    private static void setMySQLDatabase(MySQLDatabase mySQLDatabase) {
        MySQLLogger.mySQLDatabase = mySQLDatabase;
    }

    /**
     * Initialize the database with mysql-accessdata
     *
     * @param hostname
     * @param username
     * @param name
     * @param password
     * @param port
     */
    public void initialize(String hostname, String username, String name, String password, int port) {
        setMySQLDatabase(new MySQLDatabase(hostname, username, name, password, port));
        getMySQLDatabase().connect();
        createTable();
    }

    private void createTable() {
        String[] attributes = {"VARCHAR(100)", "VARCHAR(100)", "VARCHAR(100)", "TEXT"};
        String[] columns = {"Date", "LoggerType", "Class", "Message"};
        getMySQLDatabase().getMySQLHelper().createTable("MySQLLogger", attributes, columns);
    }

    public void log(LoggerType loggerType, Class currentClass, String message) {
        Date date = new Date();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        String[] content = {gregorianCalendar.getTime().toString(), loggerType.toString(), currentClass.getCanonicalName(), message};
        getMySQLDatabase().getMySQLHelper().insert(content);
    }

    public void log(Class currentClass, String message) {
        Date date = new Date();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        String[] content = {gregorianCalendar.getTime().toString(),"UNKNOWN", currentClass.getCanonicalName(), message};
        getMySQLDatabase().getMySQLHelper().insert(content);
    }

    public void log(LoggerType loggerType, String message) {
        Date date = new Date();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        String[] content = {gregorianCalendar.getTime().toString(),loggerType.toString(), "", message};
        getMySQLDatabase().getMySQLHelper().insert(content);
    }
}
