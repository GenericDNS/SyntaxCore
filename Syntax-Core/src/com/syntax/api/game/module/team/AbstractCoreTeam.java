package com.syntax.api.game.module.team;

import com.syntax.lib.exception.NotDefinedPlayer;
import org.bukkit.entity.Player;

public abstract class AbstractCoreTeam {

    public abstract void addPlayer(Player player) throws NotDefinedPlayer;

}
