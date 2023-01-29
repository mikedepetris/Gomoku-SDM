package it.units.gomokusdm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BoardFormatter {

    public static String formatBoard(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split("")).toList(), board.getBoardDimension());
        stringBuilder.append(System.lineSeparator());
        for (int row = 0; row < boardPartitionString.size(); row++) {
            stringBuilder.append(String.format("%1s", row)).append("\t");
            for (int stone = 0; stone < boardPartitionString.get(row).size(); stone++) {
                if (stone == boardPartitionString.get(row).size() - 1) {
                    stringBuilder.append(String.format("%-4s", boardPartitionString.get(row).get(stone)))
                            .append(System.lineSeparator());
                } else {
                    stringBuilder.append(String.format("%-4s", boardPartitionString.get(row).get(stone))
                            .replace(" ", "-"));
                }
            }
            if (row != boardPartitionString.size() - 1) {
                stringBuilder.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        stringBuilder.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(value ->
                        stringBuilder.append(String.format("%1s", value)).append("\t"));
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }

}
