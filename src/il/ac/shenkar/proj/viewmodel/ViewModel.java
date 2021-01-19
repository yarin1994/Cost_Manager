package il.ac.shenkar.proj.viewmodel;

import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.CostManagerException;
import il.ac.shenkar.proj.model.IModel;
import il.ac.shenkar.proj.view.IView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

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
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
