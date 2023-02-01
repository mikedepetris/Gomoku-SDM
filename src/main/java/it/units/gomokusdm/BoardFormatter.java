package it.units.gomokusdm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BoardFormatter {

    public static String formatBoard(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        // indicazioni delle colonne con il simbolo di pipe | separato da TAB
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        // [[*, *, *, *, *, *, *, *, *, *, B, W, *, *, *, *, *, *, *], [*, *, *, *, *, *, ...
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split(""))
                .toList(), board.getBoardDimension());
        // linea vuota iniziale
        stringBuilder.append(System.lineSeparator());
        for (int row = 0; row < boardPartitionString.size(); row++) {
            // numero di riga a sinistra
            stringBuilder.append(String.format("%1s", row)).append("\t");
            appendRowCells(stringBuilder, boardPartitionString, row);
            // tranne l'ultima riga inserisce le indicazioni delle colonne con il simbolo di pipe | separato da TAB
            if (row != boardPartitionString.size() - 1) {
                stringBuilder.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        // riga finale con i numeri di colonna
        stringBuilder.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(value ->
                        stringBuilder.append(String.format("%1s", value)).append("\t"));
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }

    private static void appendRowCells(StringBuilder stringBuilder, List<List<String>> boardPartitionString, int row) {
        for (int stone = 0; stone < boardPartitionString.get(row).size(); stone++) {
            // da sinistra a destra per ogni cella usa "*" o "B" o "W" separati da spazi per QUATTRO posizioni
            if (stone == boardPartitionString.get(row).size() - 1) {
                stringBuilder.append(String.format("%-4s", boardPartitionString.get(row).get(stone)))
                        .append(System.lineSeparator());
            } else {
                stringBuilder.append(String.format("%-4s", boardPartitionString.get(row).get(stone))
                        .replace(" ", "-"));
            }
        }
    }

    public static String formatBoard15to1andAtoO(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        // indicazioni delle colonne con il simbolo di pipe | separato da TAB
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        // [[*, *, *, *, *, *, *, *, *, *, B, W, *, *, *, *, *, *, *], [*, *, *, *, *, *, ...
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split(""))
                .toList(), board.getBoardDimension());
        // linea vuota iniziale
        stringBuilder.append(System.lineSeparator());
        for (int row = 0; row < boardPartitionString.size(); row++) {
            // numero di riga a sinistra 19-->1
            stringBuilder.append(String.format("%1s", boardPartitionString.size() - row)).append("\t");
            appendRowCells(stringBuilder, boardPartitionString, row);
            // tranne l'ultima riga inserisce le indicazioni delle colonne con il simbolo di pipe | separato da TAB
            if (row != boardPartitionString.size() - 1) {
                stringBuilder.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        // riga finale con i numeri di colonna a-->o
        stringBuilder.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(value ->
                        stringBuilder.append(String.format("%1c", 'a' + value)).append("\t"));
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }

    public static String formatBoardCompact(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        // [[*, *, *, *, *, *, *, *, *, *, B, W, *, *, *, *, *, *, *], [*, *, *, *, *, *, ...
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split(""))
                .toList(), board.getBoardDimension());
        // linea vuota iniziale
        stringBuilder.append(System.lineSeparator());
        for (List<String> strings : boardPartitionString) {
            for (int stone = 0; stone < strings.size(); stone++) {
                // da sinistra a destra per ogni cella usa "*" o "B" o "W"
                if (stone == strings.size() - 1) {
                    stringBuilder.append(String.format("%s", strings.get(stone)))
                            .append(System.lineSeparator());
                } else {
                    stringBuilder.append(String.format("%s", strings.get(stone))
                    );
                }
            }
        }

        return stringBuilder.toString();
    }
}
