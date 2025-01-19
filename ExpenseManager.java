import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();

    //Method to add expense to the array list, also generates a id when added
    public void addExpense(Expense expense) {
        validateDateFormat(expense.getDate());
        //finish ID creation if the expense is not read from file
        if(expense.getIsNewlyCreated()){
            expense.setID(expense.getID()+Integer.toString(duplicatedID(expense.getID()))); //Attempt to generate a unique ID       
        }
        expenses.add(expense);
    }

    //Sorts expenses by price using a comparator and lists
    public List<Expense> sortExpensesByPrice() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.naturalOrder());
        return sorted;
    }

    //Sorts expenses by date using a comparator and lists
    public List<Expense> sortExpensesByDate() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getDate));
        return sorted;
    }

    //Sorts expenses by category using a comparator and lists
    public List<Expense> sortExpensesByCategory() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getCategory));
        return sorted;
    }
    

    //Sorts expenses by Id using a comparator and lists
    public List<Expense> sortExpensesByID(){    
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getID));
        return sorted;
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


    //Bubble sort sorting method to sort expenses by price
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

    //Recursive method to calculate total cost
    public double calculateTotalExpensesRecursive() {
        return calculateTotalExpensesRecursiveHelper(expenses, 0);
    }

    //Functionaly of the recursion of total cost
    private double calculateTotalExpensesRecursiveHelper(List<Expense> expenses, int index) {
        if (index >= expenses.size()) {
            return 0;
        }
        return expenses.get(index).getPrice() + calculateTotalExpensesRecursiveHelper(expenses, index + 1);
    }


    //Validates and gets expenses within range
    public List<Expense> getExpensesWithinDateRange(String startDate, String endDate) {
        validateDateFormat(startDate);
        validateDateFormat(endDate);

        List<Expense> filtered = new ArrayList<>();
        try {
            //Gets start and end range
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            //Displays all expenses within those ranges
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

    //Checks input for date to make sure its valid using regex.
    private void validateDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Year must have 4 digits. Use YYYY-MM-DD.");
        }
    }
}
