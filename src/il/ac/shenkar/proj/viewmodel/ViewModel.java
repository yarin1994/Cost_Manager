package il.ac.shenkar.proj.viewmodel;

import il.ac.shenkar.proj.model.Category;
import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.CostManagerException;
import il.ac.shenkar.proj.model.IModel;
import il.ac.shenkar.proj.view.IView;
import org.jfree.chart.JFreeChart;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private ExecutorService pool;

    /**
     * SetView() - setting the view module of the app.
 *          addCategory() - adding a new category to DB, threaded function.
 *          addCostItem() - adding a new cost item to DB, threaded function.
 *          getCostForPie() - sending dates from view to the model for receiving table to show in pieChart
 *          getCostForTable() - sending dates from view to the model for receiving table to show in table.
 *          getCategories() - getting all the updated categories from the model
 *          getCurrencies() - returning al Currency enum members.
     */
    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    /**
     * setter function for view.
     * @param view
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }
    
    /**
        addCategory() - adding a new category to DB, threaded function.
        Sends data from DB(model) to the view.
    */
    @Override
    public void addCostItem(CostItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCostItem(item);
                    view.showMessage("Cost item was added successfully");
                    List<CostItem> items = model.getCostItems();
                    view.showItems(items);
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * deleteCostItem - sends the specific costItem id to the model for deletion.
     * @param idNumber - the costItem id.
     */
    @Override
    public void deleteCostItem(int idNumber) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deleteCostItem(idNumber);
                    view.showMessage("Cost item was deleted successfully");
                    List<CostItem> items = model.getCostItems();
                    view.showItems(items);
                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * getReport calles the getReport functino in the model and returns a list of all cost items between two dates.
     * @param start - start date.
     * @param end - end date.
     */
    @Override
    public void getReport(Date start, Date end){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CostItem> items = model.getReport(convertUtilToSql(start),convertUtilToSql(end));
                    view.showMessage("Cost item was deleted successfully");
                    view.showReport(items);
                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * addCategory sends a string with a new category name to the model.
     * @param category - string holds the name of a new category.
     */
    @Override
    public void addCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCategory(category);
                    List<Category> categories = model.printCategories();
                    view.showMessage("Category added successfully");
                    view.printCategories(categories);
                } catch (CostManagerException e) {
                    System.out.println("Could not add category!!");
                    e.printStackTrace();
                }
            }
        });
    }
/**
     * getPie calles the pieChart function in the model and returns a pieChart to view through the view.
     * @param start - start date.
     * @param end - end date.
     */
    @Override
    public void getPie(Date start , Date end){
        JFreeChart chart = null;
        try {
            chart = model.pieChart(convertUtilToSql(start), convertUtilToSql(end));
        } catch (CostManagerException e) {
            System.out.println("Could not show Pie Chart!");
            e.printStackTrace();
        }
        finally {
            view.displayPieChart(chart);
        }
    }

    /**
     * printCategories calles the function in the model and get a return value of a list holds all of the categories.
     */
    @Override
    public void printCategories(){
        pool.submit(new Runnable() {
            List<Category> categories = null;
            @Override
            public void run() {
                try {
                    categories = model.printCategories();
                    view.printCategoriesPre(categories);
                } catch (CostManagerException e) {
                    System.out.println("Could not show categories!");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * convertUtilToSql is a util function that converts the date to a sql type date.
     * @param uDate - Changes the date to a sql date type.
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
