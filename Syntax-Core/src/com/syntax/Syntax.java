package com.syntax;

import com.syntax.bukkit.listener.BukkitBridge;
import com.syntax.bukkit.modules.labymod.LabyMod;
import com.syntax.lib.FileManager;
import com.syntax.lib.console.DefaultLogger;
import com.syntax.lib.console.SyntaxConsole;
import com.syntax.lib.data.ServerData;
import com.syntax.lib.mysql.Adapter;
import com.syntax.lib.thread.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Syntax extends JavaPlugin {

    static Syntax instance;
    protected Adapter adapter;
    private BukkitBridge bukkitBridge;
    private LabyMod labyMod;
    private DefaultLogger defaultLogger;
    private SyntaxConsole syntaxConsole;
    private ServerData serverData;
    private Scheduler scheduler;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        instance = this;
        this.adapter = new Adapter("localhost", "3306", "network", "Lighdo183", "core");
        this.bukkitBridge = new BukkitBridge();
        this.labyMod = new LabyMod();
        this.defaultLogger = new DefaultLogger();
        this.defaultLogger = new DefaultLogger();
        this.syntaxConsole = new SyntaxConsole();
        this.serverData = new ServerData();
        this.scheduler = new Scheduler(UUID.randomUUID());
    }

    public DefaultLogger getDefaultLogger() {
        return defaultLogger;
    }

    public SyntaxConsole getSyntaxConsole() {
        return syntaxConsole;
    }

    public ServerData getServerData() {
        return serverData;
    }

    public static Scheduler getScheduler() {
        return new Scheduler(UUID.randomUUID());
    }

    public Scheduler getPluginScheduler() {
        return this.scheduler;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public BukkitBridge getBukkitBridge() {
        return bukkitBridge;
    }

    public LabyMod getLabyMod() {
        return labyMod;
    }

    public static Syntax getInstance() {
        return instance;
    }

    public Adapter getAdapter() {
        return adapter;
    }
}
