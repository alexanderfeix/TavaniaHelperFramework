package net.tavania.framework.logger;

import java.util.Date;
import java.util.GregorianCalendar;

/*

    Copyright © 2022 Alexander Feix
    Mail: alexander.feix03@gmail.com
    Location: TavaniaHelperFramework/net.tavania.framework.logger
    Date: 06.01.2022
    
*/
public class ConsoleLogger {

    public static ConsoleLogger instance = new ConsoleLogger();

    public static ConsoleLogger getInstance() {
        return instance;
    }

    public void log(LoggerType loggerType, Class currentclass, String message) {
        Date date = new Date();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        System.out.println("» | [" + gregorianCalendar.getTime() + "] | " + loggerType + " | " + currentclass + " | » " + message);
    }

    public void log(LoggerType loggerType, String message) {
        Date date = new Date();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        System.out.println("» | [" + gregorianCalendar.getTime() + "] | " + loggerType + " | » " + message);
    }

    public void log(Class currentclass, String message) {
        Date date = new Date();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(date);
        System.out.println("» | [" + gregorianCalendar.getTime() + "] | UNKNOWN | " + currentclass + " | » " + message);
    }

}
