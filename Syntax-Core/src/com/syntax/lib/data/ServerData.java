package com.syntax.lib.data;

import com.syntax.api.game.module.team.CoreTeam;
import com.syntax.lib.player.CorePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerData {

    protected String prefix = "§eSyntaxMC §8× §7";

    public static Collection<CorePlayer> corePlayers = new ArrayList<>();

    public static List<CoreTeam> coreTeams = new ArrayList<>();

    public String getPrefix() {
        return prefix;
    }
}
