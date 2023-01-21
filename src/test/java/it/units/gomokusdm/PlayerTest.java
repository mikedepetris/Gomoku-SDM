package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPlayerCreation() {
        Player player = new Player("xyz", Stone.BLACK);
        Assertions.assertInstanceOf(Player.class, player);
    }

    @Test
    public void testChangeUsernameOfPlayer() {
        Player player = new Player("xyz", Stone.BLACK);
        player.setUsername("qwerty");
        Assertions.assertEquals("qwerty", player.getUsername());
    }

    @Test
    public void testGetPlayerColour() {
        Player player = new Player("xyz", Stone.BLACK);
        Assertions.assertEquals(Stone.BLACK, player.getColour());
    }

    @Test
    public void testAddMoveOfStoneDoneByPlayer() {
        Player player = new Player("xyz", Stone.BLACK);
        player.addMove(new Coordinates(0, 0));
        Assertions.assertEquals(1, player.getMovesList().size());
    }

    @Test
    public void testDefaultUserBlack() {
        // - vogliamo che il test fallisca se non inserisco il nome?
        // - oppure non fallisce, ma viene mantenuto di default lo user Player 1/2 (numero scelto in base al colore)
        Player player = new Player("", Stone.BLACK);
        Assertions.assertEquals("B", player.getUsername());
    }

    @Test
    public void testDefaultUserWhite() {
        // - vogliamo che il test fallisca se non inserisco il nome?
        // - oppure non fallisce, ma viene mantenuto di default lo user Player 1/2 (numero scelto in base al colore)
        Player player = new Player("", Stone.WHITE);
        Assertions.assertEquals("W", player.getUsername());
    }


}
