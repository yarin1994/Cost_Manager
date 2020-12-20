package il.ac.shenkar.proj.model;


import java.sql.Date;

public class CostItemDemo {
    public static void main(String[] args) {
        DerbyDBModel a = null;
        try {
            a = new DerbyDBModel();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        CostItem item = new CostItem(Date.valueOf("2020-12-15"), "Food", "Nazis", 10.5,  Currency.ILS);
        Category cat = new Category("Gas");
        try {
//            System.out.println(item);
//            a.addCostItem(item);
//            a.addCategory(cat);
//            a.deleteCostItem(2);
//            a.getReport(Date.valueOf("2020-12-11"), Date.valueOf("2020-12-13"));
            a.printCostItem();
//            a.deleteCategory("Gas");
//            a.printCategories();
            a.killDB();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        System.out.println("\nDone!");
    }
}
