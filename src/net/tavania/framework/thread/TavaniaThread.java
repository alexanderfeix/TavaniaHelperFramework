package net.tavania.framework.thread;

import net.tavania.framework.logger.ConsoleLogger;
import net.tavania.framework.logger.LoggerType;
import net.tavania.framework.sql.mysql.MySQLDatabase;
import net.tavania.framework.uuid.UUIDGetter;

import java.util.ArrayList;

public class TavaniaThread {

    private static final ArrayList<Thread> runningThreads = new ArrayList<>();
    private final Thread thread;
    private String id;
    private String name;
    private String startDate;
    private boolean running;
    private Runnable runnable;

    public TavaniaThread(Runnable runnable, String name, String startDate) {
        this.id = UUIDGetter.getRandomUUID();
        this.runnable = runnable;
        this.name = name;
        this.startDate = startDate;
        this.thread = new Thread(runnable);
        this.running = false;
        startThread();
    }

    /**
     * Stopping a thread
     *
     * @param name          is the thread-name
     * @param mySQLDatabase is the mysql-connection
     */
    public static void stopThread(String name, MySQLDatabase mySQLDatabase) {
        if (getRunningThreads().contains(getThread(name))) {
            Thread thread = getThread(name);
            runningThreads.remove(thread);
            mySQLDatabase.getMySQLHelper().setValue("Name", name, "Running", false);
            ConsoleLogger.getInstance().log(LoggerType.SUCCESS, "Successfully stopped thread " + name);
        } else {
            ConsoleLogger.getInstance().log(LoggerType.ERROR, TavaniaThread.class, "Thread could not get stopped, because the thread is not running!");
        }
    }

    public static int runningThreads() {
        return Thread.activeCount();
    }

    public static boolean isRunning(String name) {
        return getRunningThreads().contains(getThread(name));
    }

    /**
     * Gets the Thread-Object from ArrayList
     *
     * @param threadName is the thread-name
     * @return thread-object
     */
    public static Thread getThread(String threadName) {
        for (int i = 0; i < getRunningThreads().size(); i++) {
            if (getRunningThreads().get(i).getName().equalsIgnoreCase(threadName)) {
                return getRunningThreads().get(i);
            }
        }
        return null;
    }

    public static ArrayList<Thread> getRunningThreads() {
        return runningThreads;
    }

    /**
     * Starting a thread, method is called instantly when creating class constructor
     */
    private void startThread() {
        getThread().setName(getName());
        getThread().start();
        runningThreads.add(getThread());
        this.running = true;
        ConsoleLogger.getInstance().log(LoggerType.SUCCESS, "Started Thread " + getName() + " with id: " + getId());
    }

    /**
     * Registers a thread in the database, columns are specified below
     *
     * @param mySQLDatabase is the mysql-connection
     */
    public void registerThread(MySQLDatabase mySQLDatabase) {
        String[] sqlAttributes = {"VARCHAR(100)", "VARCHAR(100)", "VARCHAR(100)", "BOOLEAN"};
        String[] columns = {"Id", "Name", "StartDate", "Running"};
        String[] inputValues = {getId(), getName(), getStartDate(), String.valueOf(0)};
        mySQLDatabase.getMySQLHelper().createTable("Threads", sqlAttributes, columns);
        if (!mySQLDatabase.getMySQLHelper().exists("Name", getName())) {
            mySQLDatabase.getMySQLHelper().insert(inputValues);
        }
        mySQLDatabase.getMySQLHelper().setValue("Name", getName(), "Running", true);
        ConsoleLogger.getInstance().log(LoggerType.SUCCESS, "Successfully registered thread in mysql-database!");
    }

    public Thread getThread() {
        return thread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
