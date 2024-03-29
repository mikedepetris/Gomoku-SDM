package it.units.gomokusdm;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.logging.LogManager;

import static it.units.gomokusdm.Utilities.checkInputAndGet;

@SpringBootApplication
public class GomokuSdmApplication {

    private static final String PATH_TO_LOGGING_PROPERTIES_LOADED_AS_RESOURCE = "/logging.properties";
    private static final String ARG_TO_START_GUI = "gui";
    private static final String ARG_TO_START_CLI = "cli";

    public static void main(String[] args) throws IOException {
        // Non usare il logger qui, non è inizializzato e stampa a console
        //Utilities.getLoggerOfClass(Main.class).log(Level.INFO, "main invoked");
        System.out.printf("%s %n%S %n%s %n", "*".repeat(10), "gomoku", "*".repeat(10));
        try {
            LogManager.getLogManager().readConfiguration(
                    GomokuSdmApplication.class.getResourceAsStream(PATH_TO_LOGGING_PROPERTIES_LOADED_AS_RESOURCE));
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unspecified exception");
            e.printStackTrace();
        }
        Predicate<String> isValidArg = s -> s.equalsIgnoreCase(ARG_TO_START_GUI)
                || s.equalsIgnoreCase(ARG_TO_START_CLI);

        String input = Arrays.stream(args)
                .filter(isValidArg)
                .findAny()
                .orElse("");

        if (input.equals("")) {
            String message = "Do you want to play with GUI or CLI? ";
            System.out.print(message);
            input = checkInputAndGet(isValidArg, System.out, "\n" + message);
        }
        switch (input.toLowerCase()) {
            case ARG_TO_START_GUI -> GUIMain();
            case ARG_TO_START_CLI -> CLIMain();
            default -> throw new IllegalArgumentException();
        }
    }

    private static void GUIMain() throws IOException {
        // Interfaccia Grafica:
        new GUI();
    }

    private static void CLIMain() throws IOException {
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, System.in);
        cli.initializeGameCLI();
        cli.startGameClI();
    }

}
