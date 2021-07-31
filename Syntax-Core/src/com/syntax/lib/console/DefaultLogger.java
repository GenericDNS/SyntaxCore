package com.syntax.lib.console;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultLogger extends Logger {

    public DefaultLogger() {
        super("Logger", null);
        setLevel(Level.ALL);
        setUseParentHandlers(false);
    }

}
