package it.units.gomokusdm;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerTimer {

    private TimerExpiredTask timerExpiredTask;
    private Player player;
    private Timer timer;
    private long timerDuration;
    private long playerRemainingMillis;
    private long millisWhenStartTimeIsInvoked;
    private PlayerTimerExpiredEventListener playerTimerExpiredEventListener;

    public PlayerTimer(int timerDurationInSeconds, Player player, PlayerTimerExpiredEventListener playerTimerExpiredEventListener) {
        this.timerDuration = timerDurationInSeconds * 1000L;
        this.player = player;
        timerExpiredTask = new TimerExpiredTask(player);
        playerRemainingMillis = timerDuration;
        this.playerTimerExpiredEventListener = playerTimerExpiredEventListener;
    }

    public void startTimer() {
        timer = new Timer();
        millisWhenStartTimeIsInvoked = System.currentTimeMillis();
        timer.schedule(new TimerExpiredTask(player), playerRemainingMillis);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            playerRemainingMillis -= System.currentTimeMillis() - millisWhenStartTimeIsInvoked;
        }
    }

    public long getRemaining() {
        return playerRemainingMillis;
    }

    class TimerExpiredTask extends TimerTask {

        private final Player player;

        public TimerExpiredTask(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            playerTimerExpiredEventListener.onPlayerTimerExpired(player);
        }

    }

}
