package it.units.gomokusdm;

import org.jetbrains.annotations.NotNull;

public class PlayerTimersManager {

    private Player player1;
    private Player player2;
    private final PlayerTimerExpiredEventListener listener;
    private PlayerTimer player1Timer;
    private PlayerTimer player2Timer;

    public PlayerTimersManager(Player player1, Player player2, PlayerTimerExpiredEventListener listener) {
        this.player1 = player1;
        this.player2 = player2;
        this.listener = listener;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayersTimer(int durationInSeconds) {
        player1Timer = new PlayerTimer(durationInSeconds, player1, listener);
        player2Timer = new PlayerTimer(durationInSeconds, player2, listener);
    }

    public void stopTimerCountForPlayerAndStartForTheOther(Player player) {
        if (player == player1) {
            player1Timer.stopTimer();
            player2Timer.startTimer();
        } else {
            player2Timer.stopTimer();
            player1Timer.startTimer();
        }
    }

    public void startTimerForPlayer(Player player) {
        if (player == player1) {
            player1Timer.startTimer();
        } else {
            player2Timer.startTimer();
        }
    }

}
