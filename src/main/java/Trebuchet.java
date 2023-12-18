import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Trebuchet {

    public static void main(String[] args) throws IOException {
        final Path sample = Paths.get("src/main/resources/sample.txt");
        List<String> lines = Files.readAllLines(sample);

        final Trebuchet trebuchet = new Trebuchet();
        Integer sumOfCalibration = trebuchet.calculateSumOfCalibration(lines);
        System.out.println("Sum of calibration: " + sumOfCalibration);

    }

    public Integer calculateSumOfCalibration(List<String> wordsToCalibrate) {
        return wordsToCalibrate.stream().
                mapToInt(this::getCalibration)
                .sum();
    }

    private Integer getCalibration(String word) {
        final Map.Entry<NumberAsString, Integer> firstNumberAsString = getNumberWithIndex(word, (numberAsString, str) -> str.indexOf(numberAsString.name().toLowerCase()), (numberAsString, str) -> str.indexOf(numberAsString.getIntAsChar()),
                BinaryOperator.minBy(Comparator.naturalOrder()), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? entry2 : entry1);

        final Map.Entry<NumberAsString, Integer> lastNumberAsString = getNumberWithIndex(word, (numberAsString, str) -> str.lastIndexOf(numberAsString.name().toLowerCase()), (numberAsString, str) -> str.lastIndexOf(numberAsString.getIntAsChar()),
                BinaryOperator.maxBy(Comparator.naturalOrder()), (entry1, entry2) -> entry1.getValue() < entry2.getValue() ? entry2 : entry1);


        final String result = "" + firstNumberAsString.getKey().getIntAsChar() + lastNumberAsString.getKey().getIntAsChar();
        System.out.println("result for word: " + word + "is:  " + result);
        return Integer.parseInt(result);
    }

    private Map.Entry<NumberAsString, Integer> getNumberWithIndex(String word, BiFunction<NumberAsString, String, Integer> indexOfNumberAsString, BiFunction<NumberAsString, String, Integer> indexOfNumberAsDigit, BinaryOperator<Integer> indexComparator,
                                                                  BinaryOperator<Map.Entry<NumberAsString, Integer>> mapEntryBinaryOperator) {
        return Arrays.stream(NumberAsString.values())
                .map(value -> {
                    if (indexOfNumberAsString.apply(value, word) != -1 && indexOfNumberAsDigit.apply(value, word) != -1) {
                        return Map.entry(value,
                                indexComparator.apply(indexOfNumberAsString.apply(value, word), indexOfNumberAsDigit.apply(value, word)));
                    } else if (indexOfNumberAsString.apply(value, word) != -1) {
                        return Map.entry(value, indexOfNumberAsString.apply(value, word));
                    } else if (indexOfNumberAsDigit.apply(value, word) != -1) {
                        return Map.entry(value, indexOfNumberAsDigit.apply(value, word));
                    } else {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .reduce(mapEntryBinaryOperator).orElseThrow(() -> new NoSuchElementException("No number found for word: " + word));
    }
}
