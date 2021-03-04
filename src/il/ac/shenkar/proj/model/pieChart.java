/**
 * @author Yahli Sofer - 205701212
 * @author Yarin Mizrahi - 205663917
 * */

package il.ac.shenkar.proj.model;

/**
  pieChart class.
  @params -
  categoryName - the category of the class
  total - the total of the category.
*/
public class pieChart {
    private String categoryName;
    private Double total;

    /**
     * getCategoryName funceion is a getter functin which return the category name.
     * @return - the name of the category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * setCategoryName function is a setter function which sets a new category.
     * @param categoryName - string value who holds the new category name.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * getTotal function is a getter functon.
     * @return
     */
    public Double getTotal() {
        return total;
    }

    /**
     * setTotal is a setter funciton.
     * @param total
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * pieChart C'tor.
     * @param categoryName
     * @param total
     */
    public pieChart(String categoryName, Double total) {
        this.categoryName = categoryName;
        this.total = total;
    }
}
