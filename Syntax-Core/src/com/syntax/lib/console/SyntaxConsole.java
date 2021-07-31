package com.syntax.lib.console;

import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

public class SyntaxConsole {

    private ConsoleReader consoleReader;

    private final DefaultLogger loggerHandler = new DefaultLogger();

    public void sendAsciIcon() {
        System.out.println("  _________             __                 ");
        System.out.println(" /   _____/__.__. _____/  |______  ___  ___");
        System.out.println(" \\_____  <   |  |/    \\   __\\__  \\ \\  \\/  /");
        System.out.println(" /        \\___  |   |  \\  |  / __ \\_>    < ");
        System.out.println("/_______  / ____|___|  /__| (____  /__/\\_ \\");
        System.out.println("        \\/\\/         \\/          \\/      \\/");
    }

}
