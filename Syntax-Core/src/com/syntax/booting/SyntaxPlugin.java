package com.syntax.booting;

import com.syntax.lib.configuration.file.SyntaxConfiguration;
import com.syntax.lib.mysql.Adapter;
import com.syntax.lib.network.channel.SyntaxChannel;
import com.syntax.lib.network.server.SyntaxServer;
import com.syntax.lib.thread.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class SyntaxPlugin extends JavaPlugin {

    public static SyntaxChannel getSyntaxChannel() {
        return new SyntaxChannel(new SyntaxServer("localhost", 3500, 999999));
    }

    public static SyntaxConfiguration getSyntaxConfig(Class<?> plugin) {
        return new SyntaxConfiguration(plugin.getName());
    }

    public static Adapter getPluginAdapter(Class<?> plugin) {
        return new Adapter("localhost", "3306", "network", "Lighdo183", plugin.getName());
    }

    public static Scheduler getScheduler() {
        return new Scheduler(UUID.randomUUID());
    }

}
