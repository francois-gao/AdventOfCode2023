import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Log4j2
public class Trebuchet {

    public Integer calculateSumOfCalibration(List<String> wordsToCalibrate) {
        return wordsToCalibrate.parallelStream().
                mapToInt(this::getCalibration)
                .sum();
    }

    private Integer getCalibration(String word) {
        final char firstNumber =
                IntStream.range(0, word.length()).filter(index -> Character.isDigit(word.charAt(index))).mapToObj(word::charAt).findFirst().orElseThrow(() -> new NoSuchElementException("No first number found for: "+ word));
        final char secondNumber = IntStream.iterate(word.length() - 1, operand -> operand >= 0, operand -> operand-1)
                .filter(index -> Character.isDigit(word.charAt(index))).mapToObj(word::charAt).findFirst().orElseThrow(() -> new NoSuchElementException("No last number found for: "+ word));
        return Integer.parseInt("" + firstNumber + secondNumber);
    }
}
