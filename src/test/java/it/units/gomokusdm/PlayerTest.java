package it.units.gomokusdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPlayerCreation(){
        Player player = new Player("xyz", Colour.BLACK);
        Assertions.assertInstanceOf(Player.class, player);
    }

    @Test
    public void testChangeUsernameOfPlayer(){
        Player player = new Player("xyz", Colour.BLACK);
        player.setUsername("qwerty");
        Assertions.assertEquals("qwerty", player.getUsername());
    }

    @Test
    public void test2PlayersCreationAreDifferent(){
        Player player1 = new Player("xyz", Colour.BLACK);
        Player player2 = new Player("xyz", Colour.WHITE);
        Assertions.assertNotEquals(player1, player2);
    }

    @Test
    public void testGetPlayerColour(){
        Player player = new Player("xyz", Colour.BLACK);
        Assertions.assertEquals(Colour.BLACK, player.getColour());
    }

    @Test
    public void testAddMoveOfStoneDoneByPlayer(){
        Player player = new Player("xyz", Colour.BLACK);
        player.addMove(new Coordinates(0,0));
        Assertions.assertEquals(1, player.getMovesList().size());
    }

    @Test
    public void testDefaultUserBlack(){
        // - vogliamo che il test fallisca se non inserisco il nome?
        // - oppure non fallisce, ma viene mantenuto di default lo user Player 1/2 (numero scelto in base al colore)
        Player player = new Player("", Colour.BLACK);
        Assertions.assertEquals("Player 1", player.getUsername());
    }

    @Test
    public void testDefaultUserWhite(){
        // - vogliamo che il test fallisca se non inserisco il nome?
        // - oppure non fallisce, ma viene mantenuto di default lo user Player 1/2 (numero scelto in base al colore)
        Player player = new Player("", Colour.WHITE);
        Assertions.assertEquals("Player 2", player.getUsername());
    }


}
