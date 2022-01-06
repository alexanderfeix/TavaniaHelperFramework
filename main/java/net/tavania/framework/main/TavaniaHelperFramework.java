package net.tavania.framework.main;

import net.tavania.framework.command.CommandExecuter;
import net.tavania.framework.debug.DebugManager;
import net.tavania.framework.logger.ConsoleLogger;

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
}
