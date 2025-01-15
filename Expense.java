import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Expense implements Comparable<Expense> {
    private double price;
    private String date;
    private String description;
    private String category;

    public Expense(double price, String date, String description, String category) throws IllegalArgumentException {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD, and ensure the year has four digits.");
        }
        this.price = price;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public static boolean isValidDate(String date) {
        // Regular expression to check if the date is in the format YYYY-MM-DD
         if (!date.matches("(20|19)\\d{2}-\\d{2}-\\d{2}")) { //also added a check to restrict dates to be in either 21th or 20th century
            return false;
        }

        // Use SimpleDateFormat for strict validation
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public int compareTo(Expense other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return String.format("Price: %.2f, Date: %s, Description: %s, Category: %s", price, date, description, category);
    }
}
