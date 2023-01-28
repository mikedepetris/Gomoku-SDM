package it.units.gomokusdm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BoardFormatter {

    public static String formatBoard(Board board) {
        StringBuilder tmp = new StringBuilder();
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split("")).toList(), board.getBoardDimension());
        tmp.append(System.lineSeparator());
        for (int row = 0; row < boardPartitionString.size(); row++) {
            tmp.append(String.format("%1s", row)).append("\t");
            for (int stone = 0; stone < boardPartitionString.get(row).size(); stone++) {
                if (stone == boardPartitionString.get(row).size() - 1) {
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone)))
                            .append(System.lineSeparator());
                } else {
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone))
                            .replace(" ", "-"));
                }
            }
            if (!(row == boardPartitionString.size() - 1)) {
                tmp.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        tmp.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(value ->
                        tmp.append(String.format("%1s", value)).append("\t"));
        tmp.append(System.lineSeparator());

        return tmp.toString();
    }

}
