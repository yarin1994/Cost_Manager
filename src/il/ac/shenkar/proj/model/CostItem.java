package il.ac.shenkar.proj.model;
import java.sql.Date;

public class CostItem {
    private String description;
    private double sum;
    private Currency currency;
    private String id;
    private Date date;
    private String category;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CostItem(String id, Date date, String category, String description, double sum, Currency currency) {

        setId(id);

        setDescription(description);
        setCurrency(currency);
        setSum(sum);
        //assigning id with a unique value
        //..
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
