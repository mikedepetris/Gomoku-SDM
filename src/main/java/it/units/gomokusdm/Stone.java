package it.units.gomokusdm;

import java.util.HashMap;
import java.util.Map;

// Solutions Given by https://www.baeldung.com/java-cast-int-to-enum
public enum Stone {
    EMPTY(0),
    BLACK(1),
    WHITE(2);

    private static final Map<Integer, Stone> integerToStoneMapping = new HashMap<>();

    static {
        for (Stone pizzaStatus : Stone.values()) {
            integerToStoneMapping.put(
                    pizzaStatus.getIntegerValueOfStone(),
                    pizzaStatus
            );
        }
    }

    private final int integerValueOfStone;

    Stone(int integerValueOfStone) {
        this.integerValueOfStone = integerValueOfStone;
    }

    public static Stone castIntToStone(int integerValueOfStone) {
        return integerToStoneMapping.get(integerValueOfStone);
    }

    private int getIntegerValueOfStone() {
        return integerValueOfStone;
    }
}
