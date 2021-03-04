/**
 * @author Yahli Sofer - 205701212
 * @author Yarin Mizrahi - 205663917
 * */

package il.ac.shenkar.proj.model;
import java.sql.Date;
/**
    Cost item class -
        Holds the object with the params -
        description - string
        sum - double
        currency -d
*/
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


    /**
     * CostItem C'tor, id assigned automatically with a unique value
     * @param date - this value is the date of a creation of a costItem.
     * @param category - a way to divide each cost into a category.
     * @param description - string value whih holds a few words to describe the costItem
     * @param sum - the total sum of this costItem
     * @param currency - one of the currencies availabe.
     */
    public CostItem(Date date, String category, String description, double sum, Currency currency) {
        setDate(date);
        setCategories(category);
        setDescription(description);
        setSum(sum);
        setCurrency(currency);
    }

    /**
     * CostItem C'tor, id assigned manually
     * @param id - in case we want to enter the id manually.
     * @param date - this value is the date of a creation of a costItem.
     * @param category - a way to divide each cost into a category.
     * @param description - string value whih holds a few words to describe the costItem
     * @param sum - the total sum of this costItem
     * @param currency - one of the currencies availabe.
     */
    public CostItem(int id, Date date, String category, String description, double sum, Currency currency) {
        setId(id);
        setDate(date);
        setCategories(category);
        setDescription(description);
        setSum(sum);
        setCurrency(currency);
    }
    
    /**
     * SETTERS
     */
    public void setId(int id){
        this.id= id;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setCategories(String category) {
        this.category = category;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    /**
     * GETTERS
     */
    public Date getDate() {
        return date;
    }
    public String getCategories() {
        return category;
    }
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public double getSum() {
        return sum;
    }
    public String getCurrency() {
        return String.valueOf(currency);
    }

}
