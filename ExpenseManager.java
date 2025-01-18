import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        validateDateFormat(expense.getDate());
        //finish ID creation if the expense is not read from file
        if(expense.getIsNewlyCreated()){
            expense.setID(expense.getID()+Integer.toString(duplicatedID(expense.getID()))); //Attempt to generate a unique ID       
        }
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
    
    public List<Expense> sortExpensesByID(){    //sorting the list by ID, basically a carbon copy of sort methods above
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getID));
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
    
    //Add this method to help generate unique ID
    //Should be called when adding a new entry to ensure the construct of a valid ID
    public int duplicatedID(String id){
        int duplicateAmount=0;
        for(Expense i:expenses){    //Linear search through the program, not the most efficient way
            if(id.equals(i.getID().substring(0,(i.getID().length()-1)))){
                duplicateAmount++;
            }
        }
        
        return duplicateAmount;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Expense> sortExpensesByPriceBubbleSort() {
        List<Expense> sorted = new ArrayList<>(expenses);
        int n = sorted.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sorted.get(j).getPrice() > sorted.get(j + 1).getPrice()) {
                    Expense temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }

    public double calculateTotalExpensesRecursive() {
        return calculateTotalExpensesRecursiveHelper(expenses, 0);
    }

    private double calculateTotalExpensesRecursiveHelper(List<Expense> expenses, int index) {
        if (index >= expenses.size()) {
            return 0;
        }
        return expenses.get(index).getPrice() + calculateTotalExpensesRecursiveHelper(expenses, index + 1);
    }

    public List<Expense> getExpensesWithinDateRange(String startDate, String endDate) {
        validateDateFormat(startDate);
        validateDateFormat(endDate);

        List<Expense> filtered = new ArrayList<>();
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            for (Expense expense : expenses) {
                LocalDate expenseDate = LocalDate.parse(expense.getDate());
                if ((expenseDate.isEqual(start) || expenseDate.isAfter(start)) &&
                    (expenseDate.isEqual(end) || expenseDate.isBefore(end))) {
                    filtered.add(expense);
                }
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
        return filtered;
    }

    private void validateDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Year must have 4 digits. Use YYYY-MM-DD.");
        }
    }
}
