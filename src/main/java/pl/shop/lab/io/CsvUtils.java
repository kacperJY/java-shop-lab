package pl.shop.lab.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    private static final String DELIMITER = ";";

    public static List<String[]> read(String filePath) {
        List<String[]> result = new ArrayList<>();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(path)) {
                if (line.isBlank()) continue;
                String[] splitedLine = line.split(DELIMITER, -1);
                result.add(splitedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void write(String filePath, List<String[]> rows) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>();
            for (String[] row : rows) {
                lines.add(String.join(DELIMITER, row));
            }
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
