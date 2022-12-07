import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> actions = new ArrayList<>();

    public void log(int productNum, int amount) {
        String[] arr = {Integer.toString(productNum), Integer.toString(amount)};
        actions.add(arr);
    }

    public void exportAcCSV(File txtFile) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeAll(actions);
        }
    }
}
