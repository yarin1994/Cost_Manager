package il.ac.shenkar.proj.model;

public class pieChart {
    private String categoryName;
    private Double total;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public pieChart(String categoryName, Double total) {
        this.categoryName = categoryName;
        this.total = total;
    }
}
