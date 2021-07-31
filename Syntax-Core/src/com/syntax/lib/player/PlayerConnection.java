package com.syntax.lib.player;

import java.util.UUID;

public class PlayerConnection {

    private UUID uniqueId;

    private String name;

    private String host;

    private int port;

    private boolean onlineMode;

    private int version;

    public PlayerConnection(UUID uniqueId,
                            String name,
                            String host,
                            int port,
                            boolean onlineMode,
                            int version) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.host = host;
        this.port = port;
        this.onlineMode = onlineMode;
        this.version = version;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
