package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTimerTest {

    private static final int TIMER_DURATION = 3;

    @Test
    void testTimerInitialization() {
        Player player = new Player("", Stone.WHITE);
        PlayerTimer playerTimer = new PlayerTimer(TIMER_DURATION, player, playerWithExpiredTimer -> {});

        Assertions.assertEquals(TIMER_DURATION, playerTimer.getRemaining() / 1000);
    }

    @Test
    void testTimerExpiration() throws InterruptedException {
        Player player = new Player("player", Stone.WHITE);
        PlayerTimer playerTimer = new PlayerTimer(TIMER_DURATION, player, playerWithExpiredTimer -> {
            playerWithExpiredTimer.setUsername("expiredPlayer");
        });
        playerTimer.startTimer();
        Thread.sleep(TIMER_DURATION * 1000);

        Assertions.assertEquals("expiredPlayer", player.getUsername());
    }

    @Test
    void testTimerStop() throws InterruptedException {
        Player player = new Player("player", Stone.WHITE);
        PlayerTimer playerTimer = new PlayerTimer(TIMER_DURATION, player, playerWithExpiredTimer -> {});
        playerTimer.startTimer();
        Thread.sleep(TIMER_DURATION * 1000 / 2);
        playerTimer.stopTimer();

        int expectedRemainingMillisInTimer = TIMER_DURATION * 1000 / 2;
        int deltaMillis = 200;
        Assertions.assertTrue(playerTimer.getRemaining() < expectedRemainingMillisInTimer + deltaMillis &&
                playerTimer.getRemaining() > expectedRemainingMillisInTimer - deltaMillis);
    }

    @Test
    void testTimerStopAndResume() throws InterruptedException {
        Player player = new Player("player", Stone.WHITE);
        PlayerTimer playerTimer = new PlayerTimer(TIMER_DURATION, player, playerWithExpiredTimer -> {
            playerWithExpiredTimer.setUsername("expiredPlayer");
        });
        playerTimer.startTimer();
        Thread.sleep(TIMER_DURATION * 1000 / 2);
        playerTimer.stopTimer();
        playerTimer.startTimer();
        Thread.sleep(TIMER_DURATION * 1000 / 2);

        Assertions.assertEquals("expiredPlayer", player.getUsername());
    }

}
