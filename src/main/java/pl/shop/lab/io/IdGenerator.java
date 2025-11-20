package pl.shop.lab.io;

import pl.shop.lab.App;
import pl.shop.lab.app.ApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class IdGenerator {
    private static final String PREFIX_DELIMITER = "_";
    private static final String CSV_DELIMITER = ";";

    private static final String idsCsvPath = ApplicationContext.getIdsCsvPath();

    private static String lastUserId = "usr_0";
    private static String lastProductId = "prd_0";
    private static String lastPaymentId = "pay_0";

    private static String lastOrderId = "ord_0";

    public static void initializeIds(){
        Path path = Paths.get(idsCsvPath);
        if(Files.exists(path)) {
            try {
                List<String> strings = Files.readAllLines(path); // ALWAYS ONLY ONE LINE
                String line = strings.getFirst();
                String[] split = line.split(CSV_DELIMITER);
                lastUserId = split[0];
                lastProductId = split[1];
                lastPaymentId = split[2];
                lastOrderId = split[3];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void updateIds(String id) {
        String[] split = id.split(PREFIX_DELIMITER);
        String prefix = split[0];

        switch (prefix) {
            case "usr" -> lastUserId = id;
            case "prd" -> lastProductId = id;
            case "pay" -> lastPaymentId = id;
            case "ord" -> lastOrderId = id;
        }

        String line = lastUserId + CSV_DELIMITER + lastProductId + CSV_DELIMITER +
                lastPaymentId + CSV_DELIMITER + lastOrderId;

        Path path = Paths.get(idsCsvPath);
        try {
            Files.writeString(path,line,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String nextId(String prefix) {
        String[] usrIdSplit = lastUserId.split(PREFIX_DELIMITER);
        String[] prdIdSplit = lastProductId.split(PREFIX_DELIMITER);
        String[] payIdSplit = lastPaymentId.split(PREFIX_DELIMITER);
        String[] ordIdSplit = lastOrderId.split(PREFIX_DELIMITER);
        String id = switch (prefix) {
            case "usr" -> prefix + PREFIX_DELIMITER + (Integer.parseInt(usrIdSplit[1]) + 1);
            case "prd" -> prefix + PREFIX_DELIMITER + (Integer.parseInt(prdIdSplit[1]) + 1);
            case "pay" -> prefix + PREFIX_DELIMITER + (Integer.parseInt(payIdSplit[1]) + 1);
            case "ord" -> prefix + PREFIX_DELIMITER + (Integer.parseInt(ordIdSplit[1]) + 1);
            default -> throw new IllegalStateException("Unexpected value: " + prefix);
        };
        updateIds(id);
        return id;
    }
}
