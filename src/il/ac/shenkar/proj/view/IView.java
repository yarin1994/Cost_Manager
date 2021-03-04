package il.ac.shenkar.proj.view;

import il.ac.shenkar.proj.model.Category;
import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.CostManagerException;
import il.ac.shenkar.proj.viewmodel.IViewModel;
import org.jfree.chart.JFreeChart;

import java.util.ArrayList;
import java.util.List;

public interface IView {

    public void displayPieChart(JFreeChart map);
    public void setViewModel(IViewModel vm);
    public void showMessage(String text);
    public void showItems(List<CostItem> vec);
    public void showReport(List<CostItem> vec);
    public void printCategoriesPre(List<Category> categories);
    public void printCategories(List<Category> categories);
}
