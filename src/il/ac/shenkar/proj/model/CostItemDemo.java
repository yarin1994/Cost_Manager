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
        CostItem item = new CostItem(Date.valueOf("2020-12-17"), "Bills", "gas", 60,  Currency.ILS);
//        Category cat = new Category("Gas");.
        try {
//            System.out.println(item);
//            a.addCostItem(item);
            a.pieChart(Date.valueOf("2020-12-15"), Date.valueOf("2020-12-26"));
            a.printPiechart();
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
