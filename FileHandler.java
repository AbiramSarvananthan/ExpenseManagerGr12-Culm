import java.io.*;
import java.util.*;

public class FileHandler {
    public static void saveExpensesToFile(List<Expense> expenses, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense expense : expenses) {
                writer.write(expense.getPrice() + "," + expense.getDate() + "," + expense.getDescription() + "," + expense.getCategory());
                writer.newLine();
            }
        }
    }
}
