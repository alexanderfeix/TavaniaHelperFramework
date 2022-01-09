package net.tavania.framework.main;

import net.tavania.framework.command.CommandExecuter;
import net.tavania.framework.debug.DebugManager;
import net.tavania.framework.logger.ConsoleLogger;
import net.tavania.framework.sql.mysql.MySQLDatabase;
import net.tavania.framework.thread.TavaniaThread;

/*

    Copyright © 2022 Alexander Feix
    Mail: alexander.feix03@gmail.com
    Location: TavaniaHelperFramework/net.tavania.framework.main
    Date: 06.01.2022
    
*/
public class TavaniaHelperFramework {

    public static TavaniaHelperFramework instance = new TavaniaHelperFramework();
    public DebugManager debugManager = new DebugManager();
    public CommandExecuter commandExecuter = new CommandExecuter();
    public ConsoleLogger consoleLogger = new ConsoleLogger();

    public static TavaniaHelperFramework getInstance() {
        return instance;
    }
    public DebugManager getDebugManager() {
        return debugManager;
    }

    public CommandExecuter getCommandExecuter() {
        return commandExecuter;
    }

    public ConsoleLogger getConsoleLogger() {
        return consoleLogger;
    }

    /**
     * Demo-Application with examples of the sql-methods
     * @param args
     */

    /*
    public static void main(String[] args) {
        MySQLDatabase mySQLDatabase = new MySQLDatabase("localhost", "mysql", "test", "", 3306);
        String[] array = new String[3];
        array[0] = "Id";
        array[1] = "Date";
        array[2] = "Message";
        String[] array1 = new String[3];
        array1[0] = "IdOne";
        array1[1] = "DateTwo";
        array1[2] = "MessageThree";
        String[] attributes = new String[3];
        attributes[0] = "VARCHAR(100)";
        attributes[1] = "VARCHAR(100)";
        attributes[2] = "VARCHAR(100)";
        mySQLDatabase.getMySQLHelper().createTable("TestTable1", attributes, array);
        //mySQLDatabase.getMySQLHelper().insert("Message", "Test");
        mySQLDatabase.getMySQLHelper().insert(array1);
        mySQLDatabase.getMySQLHelper().setValue("Message", "Message", "Date", "Friday");
        mySQLDatabase.getMySQLHelper().delete("Date", "Friday");
        System.out.println(mySQLDatabase.getMySQLHelper().exists("Id", "Test"));
        System.out.println(mySQLDatabase.getMySQLHelper().countAll("Id"));
        System.out.println(mySQLDatabase.getMySQLHelper().getStringValue("Message", "Test", "Id"));
        MySQLDatabase mySQLDatabase = new MySQLDatabase("localhost", "mysql", "test", "", 3306);
        TavaniaThread tavaniaThread = new TavaniaThread(new TestThread(), "TestThread", "Sunday");
        tavaniaThread.registerThread(mySQLDatabase);
        TavaniaThread.stopThread("TestThread", mySQLDatabase);
        System.out.println("Running threads: " + TavaniaThread.runningThreads());
    }
     */
}
