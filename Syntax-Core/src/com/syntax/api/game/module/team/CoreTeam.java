package com.syntax.api.game.module.team;

import com.syntax.lib.data.ServerData;
import com.syntax.lib.exception.NotDefinedPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoreTeam extends AbstractCoreTeam {

    List<Player> players = new ArrayList<>();

    private final String name;

    private int maxPlayers;

    private String prefix;

    public CoreTeam(String name, int maxPlayers, String prefix) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.prefix = prefix;
        if (!ServerData.coreTeams.contains(this)) ServerData.coreTeams.add(this);
    }

    public static CoreTeam getTeam(String name) {
        for (CoreTeam coreTeams : ServerData.coreTeams) {
            if (coreTeams.name.equalsIgnoreCase(name)) return coreTeams;
        } return null;
    }

    public static CoreTeam getTeam(Player player) {
        for (CoreTeam coreTeams : ServerData.coreTeams) {
            if (coreTeams.players.contains(player)) return coreTeams;
        } return null;
    }

    public static boolean isInTeam(Player player) {
        for (CoreTeam coreTeams : ServerData.coreTeams) {
            if (coreTeams.players.contains(player)) return true;
        } return false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public void addPlayer(Player player) {
        if (isInTeam(player)) getTeam(player).players.remove(player);
        players.add(player);
    }
}
