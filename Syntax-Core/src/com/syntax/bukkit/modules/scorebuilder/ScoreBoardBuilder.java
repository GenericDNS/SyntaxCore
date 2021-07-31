package com.syntax.bukkit.modules.scorebuilder;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.Objects;

public class ScoreBoardBuilder {
    private final Player player;
    private final Map scoreMap = Maps.newConcurrentMap();

    public ScoreBoardBuilder(Player player) {
        this.player = player;
    }

    public void setLine(int score, String prefix, String suffix) {
        this.scoreMap.put(score, prefix + ";" + suffix);
    }

    public void updateBoard(int score, String prefix, String suffix) {
        ((Team) Objects.requireNonNull(this.player.getScoreboard().getTeam("x" + score))).setSuffix(suffix);
        ((Team)Objects.requireNonNull(this.player.getScoreboard().getTeam("x" + score))).setPrefix(prefix);
    }

    public String getColorCodeByNumber(int number) {
        switch(number) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
            default:
                return "z";
        }
    }

    public void setBoard(String display) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(display);

        for(int i = 0; i < 20; ++i) {
            if (this.scoreMap.get(i) != null) {
                String[] raw = ((String)this.scoreMap.get(i)).split(";");
                Team team = scoreboard.registerNewTeam("x" + i);
                team.setPrefix(raw[0]);
                team.setSuffix(raw[1]);
                if (i < 10) {
                    team.addEntry("§" + i);
                    objective.getScore("§" + i).setScore(i);
                } else {
                    team.addEntry("§" + this.getColorCodeByNumber(i));
                    objective.getScore("§" + this.getColorCodeByNumber(i)).setScore(i);
                }

                this.player.setScoreboard(scoreboard);
            }
        }

    }

    public String getLine(int line) {
        return (String)this.scoreMap.get(line);
    }

    public Player getPlayerProfile() {
        return this.player;
    }

    public void updateDisplayName(String display) {
        Scoreboard scoreboard = this.player.getScoreboard();
        Objective objective = scoreboard.getObjective("aaa");
        objective.setDisplayName(display);
    }
}
