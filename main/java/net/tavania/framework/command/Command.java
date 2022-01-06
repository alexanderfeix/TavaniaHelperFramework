package net.tavania.framework.command;

import net.tavania.framework.logger.ConsoleLogger;

/*

    Copyright © 2022 Alexander Feix
    Mail: alexander.feix03@gmail.com
    Location: TavaniaHelperFramework/net.tavania.framework.command
    Date: 06.01.2022
    
*/
public abstract class Command {

    private final String name;
    private final String[] alias;

    public Command(String name, String... alias) {
        this.name = name;
        this.alias = alias;
    }

    public abstract void execute(final ConsoleLogger logger, final String name, final String... args);

    public String getName() {
        return name;
    }

    public String[] getAlias() {
        return alias;
    }

}
