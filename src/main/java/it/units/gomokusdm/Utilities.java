package it.units.gomokusdm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utilities {

    // Implementazione adottata presente su StackOverflow
    // https://stackoverflow.com/questions/5824825/efficient-way-to-divide-a-list-into-lists-of-n-size
    public static <T> List<List<T>> partition(List<T> list, int sizePartition) {
        if (list.size() % sizePartition != 0) throw new RuntimeException("Partition Size is wrong");
        return new ArrayList<>(IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors
                        .groupingBy(e -> e / sizePartition, Collectors
                                .mapping(list::get, Collectors.toList())))
                .values());
    }

    public static String checkInputAndGet(
            @NotNull final Predicate<String> validator,
            @NotNull final PrintStream out,
            @NotNull final String messageErrorIfInvalid) {
        return checkInputAndGet(
                Objects.requireNonNull(validator),
                Objects.requireNonNull(messageErrorIfInvalid),
                Objects.requireNonNull(out),
                IllegalArgumentException.class);
    }

    public static String checkInputAndGet(
            @NotNull final Predicate<String> validator,
            @NotNull final String messageErrorIfInvalid,
            @NotNull final PrintStream out,
            @NotNull final Class<? extends Throwable> throwable) {
        Scanner fromUser = SettableScannerSingleton
                .createNewScannerForSystemInIfAllowedOrUseTheDefaultAndGet();
        String inputValue = null;
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                inputValue = fromUser.nextLine();
            } catch (InputMismatchException ignored) {
            } catch (Exception e) {
                if (!throwable.isAssignableFrom(e.getClass())) {
                    throw e;
                }
            }
            isValidInput = validator.test(inputValue);
            if (!isValidInput) {
                Objects.requireNonNull(out).print("Invalid input. " + messageErrorIfInvalid);
            }
        }
        return inputValue;
    }

    public static class SettableScannerSingleton {

        private SettableScannerSingleton() {
        }

        public static Scanner createNewScannerForSystemInIfAllowedOrUseTheDefaultAndGet() {
            // for debugging purposes, access with reflection
            @Nullable Scanner scannerSingleInstance = new Scanner(System.in);
            return scannerSingleInstance;
        }

    }

    public static Logger getLoggerOfClass(@NotNull final Class<?> targetClass) {
        return Logger.getLogger(Objects.requireNonNull(targetClass).getCanonicalName());
    }

}
