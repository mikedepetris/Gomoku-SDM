package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CLIControllerTest {

    @Test
    void testCLICreation() {
        CLIController cli = CLIController.createInstance(System.out);
        Assertions.assertEquals(cli.getClass(), CLIController.class);
    }

    @Test
    void testGetCLI() {
        CLIController cli = CLIController.createInstance(System.out);
        Assertions.assertEquals(CLIController.getInstance(), cli);
    }
}
