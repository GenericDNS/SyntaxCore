package com.syntax.lib.player;

import com.syntax.Syntax;
import com.syntax.lib.data.ServerData;
import com.syntax.lib.types.ColorType;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CorePlayer {

    private final Executor executorService = Executors.newCachedThreadPool();

    protected boolean change = false;

    private Player remotePlayer;

    private PlayerConnection playerConnection;

    private OfflinePlayer offlinePlayer;

    private int coins;

    private ColorType mainColor;

    private ColorType secondColor;

    public void login() {
        setHashedCoins(Integer.parseInt(getCoins()));
        setHashedMainColor(getColor(getMainColor()));
        setHashedSecondColor(getColor(getSecondColor()));
    }

    public void logout() {
        if (change) {
            setCoins(getHashedCoins());
            setMainColor(getColor(getHashedMainColor()));
            setSecondColor(getColor(getHashedSecondColor()));
        }
    }

    public CorePlayer(Player remotePlayer,
                      PlayerConnection playerConnection,
                      OfflinePlayer offlinePlayer,
                      int coins, ColorType mainColor,
                      ColorType secondColor) {
        this.remotePlayer = remotePlayer;
        this.playerConnection = playerConnection;
        this.offlinePlayer = offlinePlayer;
        this.coins = coins;
        this.mainColor = mainColor;
        this.secondColor = secondColor;
    }

    public String getHashedPrefix() {
        return Syntax.getInstance()
                .getServerData()
                .getPrefix()
                .replace("§e", getHashedMainColor())
                .replace("§6", getHashedSecondColor());
    }

    public CorePlayer(Player remotePlayer,
                      PlayerConnection playerConnection,
                      OfflinePlayer offlinePlayer) {
        this.remotePlayer = remotePlayer;
        this.playerConnection = playerConnection;
        this.offlinePlayer = offlinePlayer;
    }

    public static final CorePlayer get(Player player) {
        for (CorePlayer corePlayers : ServerData.corePlayers) {
            if (corePlayers.remotePlayer() == player) return corePlayers;
        } return null;
    }

    public static final CorePlayer get(PlayerConnection connection) {
        for (CorePlayer corePlayers : ServerData.corePlayers) {
            if (corePlayers.getPlayerConnection() == connection) return corePlayers;
        } return null;
    }

    public static final CorePlayer get(OfflinePlayer offlinePlayer) {
        for (CorePlayer corePlayers : ServerData.corePlayers) {
            if (corePlayers.getOfflinePlayer() == offlinePlayer) return corePlayers;
        } return null;
    }

    public Player remotePlayer() {
        return remotePlayer;
    }

    public CorePlayer(Player remotePlayer) {
        this.remotePlayer = remotePlayer;
    }

    public void setMainColor(ColorType color) {
        executorService.execute(() -> {
            Syntax.getInstance().getAdapter().updateInTable("module_player", "mainColor", color.getColor(), "uuid", remotePlayer.getUniqueId().toString());
        });
    }

    public void setSecondColor(ColorType color) {
        executorService.execute(() -> {
            Syntax.getInstance().getAdapter().updateInTable("module_player", "seondColor", color.getColor(), "uuid", remotePlayer.getUniqueId().toString());
        });
    }

    public void setCoins(int coins) {
        executorService.execute(() ->{
            Syntax.getInstance().getAdapter().updateInTable("module_player", "coins", String.valueOf(coins), "uuid", remotePlayer.getUniqueId().toString());
        });
    }

    public String getMainColor() {
        return Syntax.getInstance().getAdapter().getEntryFromTable("module_player", "uuid", remotePlayer.getUniqueId().toString(), "mainColor");
    }

    public String getSecondColor() {
        return Syntax.getInstance().getAdapter().getEntryFromTable("module_player", "uuid", remotePlayer.getUniqueId().toString(), "secondColor");
    }

    public String getCoins() {
        return Syntax.getInstance().getAdapter().getEntryFromTable("module_player", "uuid", remotePlayer.getUniqueId().toString(), "coins");
    }

    public String getHashedMainColor() {
        return this.mainColor.getColor();
    }

    public String getHashedSecondColor() {
        this.change = true;
        return this.secondColor.getColor();
    }

    public int getHashedCoins() {
        this.change = true;
        return this.coins;
    }

    public void setHashedCoins(int coins) {
        this.change = true;
        this.coins = coins;
    }

    public void setHashedMainColor(ColorType colorType) {
        this.change = true;
        this.mainColor = colorType;
    }

    public void setHashedSecondColor(ColorType color) {
        this.change = true;
        this.secondColor = color;
    }

    private ColorType getColor(String colorCode) {
        switch (colorCode) {
            case "§0" : return ColorType.BLACK;
            case "§4" : return ColorType.RED;
            case "§9" : return ColorType.BLUE;
            case "§6" : return ColorType.ORANGE;
            case "§2" : return ColorType.GREEN;
            case "§b" : return ColorType.LIGHT_BLUE;
            case "§a" : return ColorType.LIGHT_GREEN;
            case "§c" : return ColorType.LIGHT_RED;
            case "§d" : return ColorType.PINK;
            case "§f" : return ColorType.WHITE;
            case "§e" : return ColorType.YELLOW;
            default : return null;
        }
    }

    public PlayerConnection getPlayerConnection() {
        return playerConnection;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setPlayerConnection(PlayerConnection playerConnection) {
        this.playerConnection = playerConnection;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }
}
