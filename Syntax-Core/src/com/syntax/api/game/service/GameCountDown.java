package com.syntax.api.game.service;

import com.syntax.Syntax;

public class GameCountDown {

    int index;

    private ExecuteOnIndex execute;

    public GameCountDown(int index) {
        this.index = index;
    }

    public void startScheduler(int delaySeconds) {
        Syntax.getScheduler().scheduleTask(new Runnable() {

            @Override
            public void run() {

                if (execute != null) execute.execute();
                index--;

            }
        }, delaySeconds);
    }

    public void listen(ExecuteOnIndex execute) {
        this.execute = execute;
    }

    public void listen(int listenedIndex, ExecuteOnIndex execute) {
        if (this.index == listenedIndex) execute.execute();
    }

}
