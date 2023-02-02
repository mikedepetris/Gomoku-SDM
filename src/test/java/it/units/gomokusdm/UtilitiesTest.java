package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class UtilitiesTest {

    private static Stream<Arguments> generateListAndPartitionToCreate() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), 3),
                Arguments.of(Arrays.asList("A", "B", "C", "D"), 2));
    }

    @ParameterizedTest
    @MethodSource("generateListAndPartitionToCreate")
    void testPartitionList(List<Integer> list, int size) {
        Assertions.assertEquals(size, Utilities.partition(list, size).size());
    }

}
