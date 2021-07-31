package com.syntax.lib.player;

import java.util.UUID;

public class OfflinePlayer {

    protected UUID uniqueID;

    protected String name;

    protected Long lastLogin;

    protected Long firstLogin;

    public OfflinePlayer(UUID uniqueID,
                         String name,
                         Long lastLogin,
                         Long firstLogin) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.lastLogin = lastLogin;
        this.firstLogin = firstLogin;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UUID uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Long firstLogin) {
        this.firstLogin = firstLogin;
    }
}
