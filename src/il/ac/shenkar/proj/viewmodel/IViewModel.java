package il.ac.shenkar.proj.viewmodel;

import il.ac.shenkar.proj.model.Category;
import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.IModel;
import il.ac.shenkar.proj.view.IView;
import org.jfree.chart.JFreeChart;

import java.util.Date;
import java.util.List;

public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);
    public void addCostItem(CostItem item);
    public void deleteCostItem(int idNumber);
    public void getReport(Date start, Date end);
    public void addCategory(Category category);
    public void printCategories(); // change the return value

    public JFreeChart getPie(Date start, Date end);
}
