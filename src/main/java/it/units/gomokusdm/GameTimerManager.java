package it.units.gomokusdm;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class MyTask extends TimerTask {

    private Player player;

    public MyTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        //callback func
    }

}

public class GameTimerManager {

    private MyTask player1Task;
    private MyTask player2Task;
    private Player player1;
    private Player player2;
    private Timer timer;

    private long timerDuration;
    private long player1RemainingMillis;
    private long player2RemainingMillis;

    private long millisWhenStartTimeIsInvoked;
    private Date date;

    public GameTimerManager(long timerDuration, Player player1, Player player2) {
        this.timerDuration = timerDuration;
        this.player1 = player1;
        this.player2 = player2;
        player1Task = new MyTask(player1);
        player2Task = new MyTask(player2);
        timer = new Timer();
        player1RemainingMillis = timerDuration;
        player2RemainingMillis = timerDuration;
        date = new Date();
    }

    public void startTimerOfPlayer(Player player) {
        millisWhenStartTimeIsInvoked = date.getTime();
        if (player == player1) {
            timer.schedule(player1Task, player1RemainingMillis);
        } else if (player == player2) {
            timer.schedule(player2Task, player1RemainingMillis);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void pauseTimerOfPlayer(Player player) {
        timer.cancel();
        if (player == player1) {
            player1RemainingMillis -= date.getTime() - millisWhenStartTimeIsInvoked;
        } else if (player == player2) {
            player2RemainingMillis -= date.getTime() - millisWhenStartTimeIsInvoked;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public long getRemainingTimeOfPlayer(Player player) {
        if (player != player1 && player != player2) {
            throw new IllegalArgumentException();
        }
        return player == player1? player1RemainingMillis : player2RemainingMillis;
    }

}
