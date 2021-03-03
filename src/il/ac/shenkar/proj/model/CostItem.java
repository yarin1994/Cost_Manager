package il.ac.shenkar.proj.model;
import java.sql.Date;

public class CostItem {
    private String description;
    private double sum;
    private Currency currency;
    private int id;
    private Date date;
    private String category;



    @Override
    public String toString() {
        return "CostItem:" + '\t' +
                "id='" + id + '\'' + '\t' +
                "|      date=" + date + '\t' +
                "|      category='" + category + '\'' + '\t' +
                "|      description='" + description + '\'' + '\t' +
                "|      sum=" + sum + '\t' +
                "|      currency=" + currency + '\t' ;
    }

    public CostItem(String category, String description, double sum, Currency currency) {
        setCategories(category);
        setDescription(description);
        setSum(sum);
        setCurrency(currency);
        //assigning id with a unique value
        //..
    }

    public CostItem(Date date, String category, String description, double sum, Currency currency) {
        setDate(date);
        setCategories(category);
        setDescription(description);
        setSum(sum);
        setCurrency(currency);
        //assigning id with a unique value
        //..
    }
    public CostItem(int id, Date date, String category, String description, double sum, Currency currency) {
        setId(id);
        setDate(date);
        setCategories(category);
        setDescription(description);
        setSum(sum);
        setCurrency(currency);
        //assigning id with a unique value
        //..
    }



    public void setId(int id){
        this.id= id;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategories() {
        return category;
    }

    public void setCategories(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return String.valueOf(currency);
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
