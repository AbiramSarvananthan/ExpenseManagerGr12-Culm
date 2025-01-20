import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExpenseManager {

    private List<Expense> expenses = new ArrayList<>();  // List to store all expenses

    // Method to add an expense to the list and generate a unique ID if necessary
    public void addExpense(Expense expense) {
        validateDateFormat(expense.getDate());  // Validate the date format of the expense
        // If the expense is newly created, generate a unique ID
        if (expense.getIsNewlyCreated()) {
            // Generate a unique ID by appending a duplicate count to the existing ID
            expense.setID(expense.getID() + Integer.toString(duplicatedID(expense.getID())));
        }
        expenses.add(expense);  // Add the expense to the list
    }

    // Sorts the expenses by price in ascending order using a comparator
    public List<Expense> sortExpensesByPrice() {
        List<Expense> sorted = new ArrayList<>(expenses);  // Copy expenses to avoid modifying the original list
        sorted.sort(Comparator.naturalOrder());  // Sort by price using natural order (assuming Expense implements Comparable)
        return sorted;
    }

    // Sorts the expenses by date in ascending order using a comparator
    public List<Expense> sortExpensesByDate() {
        List<Expense> sorted = new ArrayList<>(expenses);  // Copy expenses to avoid modifying the original list
        sorted.sort(Comparator.comparing(Expense::getDate));  // Sort by date using the Expense's getDate method
        return sorted;
    }

    // Sorts the expenses by category in ascending order using a comparator
    public List<Expense> sortExpensesByCategory() {
        List<Expense> sorted = new ArrayList<>(expenses);  // Copy expenses to avoid modifying the original list
        sorted.sort(Comparator.comparing(Expense::getCategory));  // Sort by category using the Expense's getCategory method
        return sorted;
    }

    // Sorts the expenses by ID in ascending order using a comparator
    public List<Expense> sortExpensesByID() {
        List<Expense> sorted = new ArrayList<>(expenses);  // Copy expenses to avoid modifying the original list
        sorted.sort(Comparator.comparing(Expense::getID));  // Sort by ID using the Expense's getID method
        return sorted;
    }

    // Method to generate a unique ID by checking how many times the ID has been used
    // and appending a counter to make it unique
    public int duplicatedID(String id) {
        int duplicateAmount = 0;
        for (Expense i : expenses) {  // Loop through the expenses list
            if (id.equals(i.getID().substring(0, (i.getID().length() - 1)))) {  // Check if the base ID matches
                duplicateAmount++;  // Increment duplicate count
            }
        }
        return duplicateAmount;  // Return the number of duplicates
    }

    // Returns the list of all expenses
    public List<Expense> getExpenses() {
        return expenses;
    }

    // Bubble sort method to sort expenses by price in ascending order
    public List<Expense> sortExpensesByPriceBubbleSort() {
        List<Expense> sorted = new ArrayList<>(expenses);  // Copy expenses to avoid modifying the original list
        int n = sorted.size();
        for (int i = 0; i < n - 1; i++) {  // Outer loop
            for (int j = 0; j < n - i - 1; j++) {  // Inner loop for comparing adjacent elements
                // If the current expense's price is greater than the next one, swap them
                if (sorted.get(j).getPrice() > sorted.get(j + 1).getPrice()) {
                    Expense temp = sorted.get(j);  // Temporary variable for swapping
                    sorted.set(j, sorted.get(j + 1));  // Swap elements
                    sorted.set(j + 1, temp);  // Swap elements
                }
            }
        }
        return sorted;
    }

    // Recursive method to calculate the total cost of all expenses
    public double calculateTotalExpensesRecursive() {
        return calculateTotalExpensesRecursiveHelper(expenses, 0);  // Call the helper method with initial index 0
    }

    // Helper method for recursive total calculation
    private double calculateTotalExpensesRecursiveHelper(List<Expense> expenses, int index) {
        if (index >= expenses.size()) {  // Base case: if the index is out of bounds, return 0
            return 0;
        }
        // Recursive case: return the price of the current expense plus the sum of remaining expenses
        return expenses.get(index).getPrice() + calculateTotalExpensesRecursiveHelper(expenses, index + 1);
    }

    // Method to get expenses within a specified date range
    public List<Expense> getExpensesWithinDateRange(String startDate, String endDate) {
        validateDateFormat(startDate);  // Validate the start date format
        validateDateFormat(endDate);  // Validate the end date format

        List<Expense> filtered = new ArrayList<>();  // List to store expenses within the date range
        try {
            // Parse the start and end dates
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            // Filter expenses within the date range
            for (Expense expense : expenses) {
                LocalDate expenseDate = LocalDate.parse(expense.getDate());
                if ((expenseDate.isEqual(start) || expenseDate.isAfter(start)) &&
                    (expenseDate.isEqual(end) || expenseDate.isBefore(end))) {
                    filtered.add(expense);  // Add expense to filtered list if within the range
                }
            }
        } catch (DateTimeParseException e) {  // Catch invalid date format errors
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
        return filtered;  // Return the filtered list of expenses
    }

    // Helper method to validate the format of a date string using regex (YYYY-MM-DD)
    private void validateDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {  // Check if the date format is valid
            throw new IllegalArgumentException("Invalid date format. Year must have 4 digits. Use YYYY-MM-DD.");
        }
    }
}
