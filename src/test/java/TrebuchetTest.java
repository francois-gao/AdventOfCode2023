import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class TrebuchetTest {

    final Path sample = Paths.get("src/main/resources/sample.txt");
    final Trebuchet trebuchet= new Trebuchet();

    @Test
    public void should_return_calibration() throws IOException {
        List<String> lines = Files.readAllLines(sample);
        Integer sumOfCalibration = trebuchet.calculateSumOfCalibration(lines);
        Assertions.assertThat(sumOfCalibration).isNotNull();
        Assertions.assertThat(sumOfCalibration).isEqualTo(55538);
    }

}