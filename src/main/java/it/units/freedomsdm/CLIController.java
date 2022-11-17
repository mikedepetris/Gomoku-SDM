package it.units.freedomsdm;

import java.io.PrintStream;

public class CLIController {

    private static CLIController cli = null;

    private PrintStream outputStream;
    private Board board;
    private Game game;
    private Player player1;
    private Player player2;

    public static CLIController createInstance(PrintStream outputStream) {
        if (cli == null) cli = new CLIController(outputStream);
        return cli;
    }

    public static CLIController getInstance() {
        return cli;
    }

    private CLIController(PrintStream outputStream) {
        this.outputStream = outputStream;
    }


}
