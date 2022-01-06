package net.tavania.framework.debug;

/*

    Copyright © 2022 Alexander Feix
    Mail: alexander.feix03@gmail.com
    Location: TavaniaHelperFramework/net.tavania.framework.debug
    Date: 06.01.2022
    
*/
public class DebugManager {

    public void debug(int priority) {
        int line = new Throwable().getStackTrace()[0].getLineNumber();
        switch (priority) {
            case 0 -> System.out.println("[DEBUG THROWN in line " + line + "]");
            case 1 -> System.out.println("[ERROR in line " + line + "]");
            case 2 -> System.out.println("[WARNING in line " + line + "]");
            case 3 -> System.out.println("[INFORMATION in line " + line + "]");
        }
    }

}
