import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Expense implements Comparable<Expense> {
    private double price;
    private String date;
    private String description;
    private String category;

    public Expense(double price, String date, String description, String category) throws IllegalArgumentException {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
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
