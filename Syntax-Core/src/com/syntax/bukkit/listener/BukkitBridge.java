package com.syntax.bukkit.listener;

import com.syntax.Syntax;
import com.syntax.lib.player.CorePlayer;
import com.syntax.lib.player.OfflinePlayer;
import com.syntax.lib.player.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class BukkitBridge implements Listener {

    public BukkitBridge() {
        Bukkit.getPluginManager().registerEvents(new BukkitBridge(), Syntax.getInstance());
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        CorePlayer corePlayer = new CorePlayer(event.getPlayer());
        corePlayer.login();
    }

    @EventHandler
    public void handle(PlayerKickEvent event) {
        CorePlayer.get(event.getPlayer()).logout();
    }

    @EventHandler
    public void handle(PlayerLoginEvent event) {
        String address = ((CraftPlayer) event.getPlayer())
                .getHandle()
                .playerConnection
                .networkManager
                .getRawAddress()
                .toString();
        CorePlayer.get(event.getPlayer()).
                setOfflinePlayer(new OfflinePlayer(
                        event.getPlayer().getUniqueId(),
                        event.getPlayer().getName(),
                        System.currentTimeMillis(),
                        System.currentTimeMillis()));
        CorePlayer.get(event.getPlayer()).
                setPlayerConnection(new PlayerConnection(
                        event.getPlayer().getUniqueId(),
                        event.getPlayer().getName(),
                        event.getRealAddress().getHostAddress(),
                        Syntax.getInstance().getServer().getPort(),
                        false,
                        8));
    }

}
