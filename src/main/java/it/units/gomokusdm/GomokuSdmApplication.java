package it.units.gomokusdm;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GomokuSdmApplication {

    public static void main(String[] args) throws IOException {
        // CLI Controller:
        //SpringApplication.run(GomokuSdmApplication.class, args);
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, System.in);
        cli.initializeGameCLI();
        cli.startGameClI();

        // Interfaccia Grafica:
        new GUI();


    }

}
