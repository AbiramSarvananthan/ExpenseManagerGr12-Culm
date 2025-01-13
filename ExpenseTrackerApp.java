import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class ExpenseTrackerApp {
    private static ExpenseManager manager = new ExpenseManager();
    private static final String[] CATEGORIES = {"Food", "Travel", "Entertainment", "Utilities", "Other"};

    public static void main(String[] args) {


        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExpenseTrackerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpenseTrackerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpenseTrackerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpenseTrackerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        JFrame frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>(CATEGORIES);

        JButton addButton = new JButton("Add Expense");
        JButton saveButton = new JButton("Save to File");
        JButton displayButton = new JButton("Display All Expenses");
        JButton sortDateButton = new JButton("Sort by Date");
        JButton sortCategoryButton = new JButton("Sort by Category");
        JButton calculateButton = new JButton("Calculate Total");

        JTextArea outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        addButton.addActionListener(e -> {
            try {
                double price = Double.parseDouble(priceField.getText());
                String date = dateField.getText();
                String description = descriptionField.getText();
                String category = (String) categoryComboBox.getSelectedItem();

                if (!Expense.isValidDate(date)) {
                    throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
                }

                manager.addExpense(new Expense(price, date, description, category));
                outputArea.append("Expense added successfully!\n");
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid price input.\n");
            } catch (IllegalArgumentException ex) {
                outputArea.append(ex.getMessage() + "\n");
            }
        });

        saveButton.addActionListener(e -> {
            try {
                FileHandler.saveExpensesToFile(manager.getExpenses(), "expenses.txt");
                outputArea.append("Expenses saved to file successfully!\n");
            } catch (IOException ex) {
                outputArea.append("Error saving expenses to file.\n");
            }
        });

        displayButton.addActionListener(e -> {
            List<Expense> allExpenses = manager.getExpenses();
            if (allExpenses.isEmpty()) {
                outputArea.append("No expenses to display.\n");
            } else {
                outputArea.append("Displaying all expenses:\n");
                for (Expense expense : allExpenses) {
                    outputArea.append(expense.toString() + "\n");
                }
            }
        });

        sortDateButton.addActionListener(e -> {
            List<Expense> sortedByDate = manager.sortExpensesByDate();
            outputArea.append("Expenses sorted by date:\n");
            for (Expense expense : sortedByDate) {
                outputArea.append(expense.toString() + "\n");
            }
        });

        sortCategoryButton.addActionListener(e -> {
            List<Expense> sortedByCategory = manager.sortExpensesByCategory();
            outputArea.append("Expenses sorted by category:\n");
            for (Expense expense : sortedByCategory) {
                outputArea.append(expense.toString() + "\n");
            }
        });

        calculateButton.addActionListener(e -> {
            double total = manager.calculateTotalExpenses();
            outputArea.append("Total Expenses: " + total + "\n");
        });

        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(categoryLabel);
        panel.add(categoryComboBox);
        panel.add(addButton);
        panel.add(saveButton);
        panel.add(displayButton);
        panel.add(sortDateButton);
        panel.add(sortCategoryButton);
        panel.add(calculateButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
