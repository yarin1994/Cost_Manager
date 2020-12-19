package il.ac.shenkar.proj.model;


import java.sql.Date;

public class CostItemDemo {
    public static void main(String[] args) {
        DerbyDBModel a = new DerbyDBModel();
        CostItem item = new CostItem("1", Date.valueOf("2020-12-18"), "Food", "Mcdonalds", 100,  Currency.EURO);
        try {
            a.addCostItem(item);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }
}
