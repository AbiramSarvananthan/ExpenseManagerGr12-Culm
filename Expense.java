import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Expense implements Comparable<Expense> {
    private double price;
    private String date;
    private String description;
    private String category;
    private String title;   //limited length, used for displaying gross information
    private String id;
    private boolean isNewlyCreated; //to extinguish between a newly created Expense and one read from the file

    public Expense(double price, String date, String description, String category, String title) throws IllegalArgumentException {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD, and ensure the year is within 20th/21st century.");
        }
        if(price<=0){
            throw new IllegalArgumentException("Invalid Price. Please make sure the value is greater than zero.");
        }
        if(title.isBlank()||title.length()>25){
            throw new IllegalArgumentException("Invalid Title. Please make sure its length is between 0 and 25 characters");
        }
        this.price = price;
        this.date = date;
        this.description = description;
        this.category = category;
        this.title=title;
        
        //Generate an basic ID 
        String[] dateSplited=date.split("-");
        this.id=dateSplited[0]+category.substring(0,1).toUpperCase(); //YYYY<Initial of the category in capital>
        
        //Newly created
        isNewlyCreated=true;
    }
    
    //overloading constructor
    public Expense(double price, String date, String description, String category, String id, String title) throws IllegalArgumentException {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD, and ensure the year has four digits.");
        }
        if(price<=0){
            throw new IllegalArgumentException("Invalid Price. Please make sure the value is greater than zero.");
        }
        if(title.isBlank()||title.length()>25){
            throw new IllegalArgumentException("Invalid Title. Please make sure its length is between 0 and 25 characters");
        }
        this.price = price;
        this.date = date;
        this.description = description;
        this.category = category;
        this.title=title;
        //read from file
        this.id=id;
        isNewlyCreated=false;   
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
    
    public String getTitle() {
        return title;
    }
    
    protected void setID(String id){
        this.id=id;
    }
    
    public String getID(){
        return id;
    }
    
    public boolean getIsNewlyCreated(){
        return isNewlyCreated;
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
    public String toString() {  //complete printout of information
        String output="["+id+"] ";
        output+=String.format("Price: %.2f, Date: %s, Title: \"%s\", Description: %s, Category: %s", price, date, title, description, category);
        return output;
    }
    //Method overloading
    public String toString(int i){  //used for displaying gross information, I admit this is quite skatchy
        String output="["+id+"] ";
        output+=String.format("Price: %.2f, Date: %s, Title: \"%s\", Category: %s", price, date, title, category);
        return output;
    }
}
