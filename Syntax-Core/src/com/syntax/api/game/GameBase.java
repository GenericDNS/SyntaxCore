package com.syntax.api.game;

import com.syntax.api.game.service.GameStates;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class GameBase extends JavaPlugin {

    private GameStates currentGameState;

    public void setGameState(GameStates gameState) {
        this.currentGameState = gameState;
        onGameStatesChange();
        switch (gameState) {
            case LOBBY: this.onEnterLobby();
                break;
            case STARTING: this.onEnterStarting();
                break;
            case VOTED: this.onEnterVoted();
                break;
            case RUNNING: this.onEnterRunning();
                break;
            case END_FIGHT: this.onEnterFight();
                break;
            case GAME_END: this.onEnterGameEnd();
                break;
            case RESETTING: this.onEnterResetting();
                break;
        }
    }

    public GameStates getCurrentGameState() {
        return currentGameState;
    }

    public void onEnterStarting() {

    };

    public void onEnterVoted() {

    };

    public void onEnterRunning() {

    };

    public void onEnterFight() {

    };

    public void onEnterGameEnd() {

    };

    public void onEnterResetting() {

    };

    public void onEnterLobby() {

    };

    public void onGameStatesChange() {

    };

}
