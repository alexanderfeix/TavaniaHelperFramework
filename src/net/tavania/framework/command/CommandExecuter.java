package net.tavania.framework.command;

import net.tavania.framework.logger.ConsoleLogger;

import java.util.Arrays;
import java.util.HashMap;

/*

    Copyright © 2022 Alexander Feix
    Mail: alexander.feix03@gmail.com
    Location: TavaniaHelperFramework/net.tavania.framework.command
    Date: 06.01.2022
    
*/
public class CommandExecuter {

    private final HashMap<String, Command> commandHashMap = new HashMap<String, Command>();

    public void register(final Command command) {
        commandHashMap.put(command.getName(), command);
        for (String alias : command.getAlias()) {
            commandHashMap.put(alias, command);
        }
    }

    public void unregister(final Command command) {
        commandHashMap.remove(command.getName(), command);
        for (String alias : command.getAlias()) {
            commandHashMap.remove(alias, command);
        }
    }

    public boolean dispatchCommand(final String commandLine) {
        final String[] split = commandLine.split(" ", -1);
        final String[] args = Arrays.copyOfRange(split, 1, split.length);
        final String commandName = split[0];
        final Command command = commandHashMap.get(commandName);
        if (command == null) return false;
        command.execute(ConsoleLogger.getInstance(), commandName, args);
        return true;
    }

    public HashMap<String, Command> getCommandHashMap() {
        return commandHashMap;
    }

}
