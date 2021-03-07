package pl.com.gosia.api.invoice.company;


import io.vavr.collection.Stream;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class NipUtils {

    private NipUtils() {
        throw new AssertionError();
    }

    static public String clearNip(String nip) {
        return nip.replaceAll("\\D", "");
    }

    static public void validateNip(String nip) {

        if (nip.length() != 10) {
            throw new IllegalArgumentException("Incorrect number of digits in nip");
        }

        if (!checkDigit(nip)) {
            throw new IllegalArgumentException("Incorrect sum control");
        }
    }

    private static boolean checkDigit(String nip) {
        final var nipDigits = stream(nip.split(""))
                .map(Integer::parseInt)
                .collect(toList());

        final var digitsToSum = nipDigits.stream()
                .limit(9)
                .collect(toList());

        final var nipWages = List.of(6, 5, 7, 2, 3, 4, 5, 6, 7);

        final var sum = Stream.ofAll(digitsToSum).zip(nipWages).map(el -> el._1 * el._2).sum().intValue();

        return nipDigits.get(9) == (sum % 11);
    }


}
