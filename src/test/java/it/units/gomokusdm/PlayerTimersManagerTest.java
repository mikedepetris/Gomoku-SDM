package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTimersManagerTest {

    private static final int TIMER_DURATION_IN_SECONDS = 1;

    @Test
    void testPlayerTimersSetting() {
        Player player1 = new Player("one", Stone.BLACK);
        Player player2 = new Player("two", Stone.WHITE);
        PlayerTimersManager playerTimersManager = new PlayerTimersManager(player1, player2, player -> {});

        Assertions.assertEquals(player1, playerTimersManager.getPlayer1());
        Assertions.assertEquals(player2, playerTimersManager.getPlayer2());
    }

    @Test
    void testPlayerTimersExpiration() throws InterruptedException {
        Player player1 = new Player("one", Stone.BLACK);
        Player player2 = new Player("two", Stone.WHITE);
        PlayerTimersManager playerTimersManager = new PlayerTimersManager(player1, player2, player -> {
            player.setUsername("two with expired timer");
        });
        playerTimersManager.setPlayersTimer(TIMER_DURATION_IN_SECONDS);
        playerTimersManager.startTimerForPlayer(player2);

        Assertions.assertEquals("two", player2.getUsername());
        Thread.sleep(TIMER_DURATION_IN_SECONDS * 1000 + 100);
        Assertions.assertEquals("two with expired timer", player2.getUsername());
    }

    @Test
    void testChangePlayerTimerCounting() throws InterruptedException {
        Player player1 = new Player("one", Stone.BLACK);
        Player player2 = new Player("two", Stone.WHITE);
        PlayerTimersManager playerTimersManager = new PlayerTimersManager(player1, player2, player -> {
            player.setUsername("one with expired timer");
        });
        playerTimersManager.setPlayersTimer(TIMER_DURATION_IN_SECONDS);
        playerTimersManager.startTimerForPlayer(player2);
        Thread.sleep(TIMER_DURATION_IN_SECONDS * 1000 / 2);
        playerTimersManager.stopTimerCountForPlayerAndStartForTheOther(player2);
        Thread.sleep(TIMER_DURATION_IN_SECONDS * 1000 + 100);

        Assertions.assertEquals("one with expired timer", player1.getUsername());
    }

}
