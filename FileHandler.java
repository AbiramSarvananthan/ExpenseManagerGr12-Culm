import java.io.*;
import java.util.*;

class FileHandler {
    public static void saveExpensesToFile(List<Expense> expenses, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense expense : expenses) {
                writer.write(expense.getPrice() + "," + expense.getDate() + "," + expense.getDescription() + ","
                        + expense.getCategory() + "," + expense.getID());
                writer.newLine();
            }
        }
    }

    public static List<Expense> loadExpensesFromFile(String fileName) throws IOException {
        List<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    double price = Double.parseDouble(parts[0]);
                    String date = parts[1];
                    String description = parts[2];
                    String category = parts[3];
                    String id = parts[4];
                    expenses.add(new Expense(price, date, description, category, id));
                }
            }
        }
        return expenses;
    }
}
