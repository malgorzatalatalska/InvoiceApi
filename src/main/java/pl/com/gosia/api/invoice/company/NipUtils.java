package pl.com.gosia.api.invoice.company;


import io.vavr.collection.Stream;
import io.vavr.control.Either;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

class NipUtils {

    private static final Predicate<String> NIP_PATTERN = Pattern.compile("[0-9]{10}").asMatchPredicate();
    private static final List<Integer> NIP_WEIGHTS = List.of(6, 5, 7, 2, 3, 4, 5, 6, 7);

    private NipUtils() {
        throw new AssertionError();
    }

    static String clearNip(String nip) {
        return nip.replaceAll("\\D", "");
    }

    static Either<String, String> validateNip(String nip) {

        if (NIP_PATTERN.negate().test(nip)) {
            return Either.left("NIP must be 10 digits");
        }

        if (!checkControlSum(nip)) {
            return Either.left("NIP must have correct control sum");
        }

        return Either.right(nip);
    }

    private static boolean checkControlSum(String nip) {
        final var nipDigits = stream(nip.split(""))
                .map(Integer::parseInt)
                .collect(toList());

        final var digitsToSum = nipDigits.stream()
                .limit(9)
                .collect(toList());

        final var sum = Stream.ofAll(digitsToSum).zip(NIP_WEIGHTS).map(el -> el._1 * el._2).sum().intValue();

        return nipDigits.get(9) == (sum % 11);
    }


}
