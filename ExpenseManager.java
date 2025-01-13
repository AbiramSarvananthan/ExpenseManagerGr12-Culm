import java.util.*;

public class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> sortExpensesByPrice() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.naturalOrder());
        return sorted;
    }

    public List<Expense> sortExpensesByDate() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getDate));
        return sorted;
    }

    public List<Expense> sortExpensesByCategory() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getCategory));
        return sorted;
    }

    public double calculateTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getPrice).sum();
    }

    public Expense searchExpenseByDescription(String description) {
        for (Expense expense : expenses) {
            if (expense.getDescription().equalsIgnoreCase(description)) {
                return expense;
            }
        }
        return null;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    // Bubble sort implementation for sorting by price
    public List<Expense> sortExpensesByPriceBubbleSort() {
        List<Expense> sorted = new ArrayList<>(expenses);
        int n = sorted.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sorted.get(j).getPrice() > sorted.get(j + 1).getPrice()) {
                    // Swap
                    Expense temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }
}
