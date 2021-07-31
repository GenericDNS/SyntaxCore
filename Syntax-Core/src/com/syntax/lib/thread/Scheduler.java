package com.syntax.lib.thread;

import java.util.*;

public final class Scheduler implements ScheduleCanceler {

    static Collection<Scheduler> activesScheduler = new ArrayList<>();

    protected int ticks;

    private boolean interrupted = false;

    private UUID uniqueID;

    protected Timer timerService;

    public Scheduler(UUID uniqueID) {
        this.uniqueID = uniqueID;
        this.timerService = new Timer();
        if (!activesScheduler.contains(this))
            activesScheduler.add(this);
    }

    public static void newScheduleTask() {
        Scheduler scheduler = new Scheduler(UUID.randomUUID());
        scheduler.interrupted = false;
    }

    public void scheduleTask(Runnable runnable, int d) {
        this.timerService.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        } ,d %1000);
    }

    public void scheduleTask(ScheduleExecute scheduleExecute, int d) {
        this.timerService.schedule(new TimerTask() {
            @Override
            public void run() {
                scheduleExecute.execute();
            }
        }, d);
    }

    public void scheduleTask(Runnable runnable, int d, int p) {
        this.timerService.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        } ,d%1000, p%1000);
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public int getTicks() {
        return ticks;
    }

    @Override
    public void cancel() {
        activesScheduler.remove(this);
        this.timerService.cancel();
    }

    public static Scheduler get(UUID uniqueID) {
        for (Scheduler scheduler : activesScheduler) {
            if (scheduler.uniqueID == uniqueID) return scheduler;
        } return null;
    }

    @Override
    public void cancelAllTasks() {
        for (Scheduler schedulers : activesScheduler) {
            schedulers.cancel();
        }
    }
}
