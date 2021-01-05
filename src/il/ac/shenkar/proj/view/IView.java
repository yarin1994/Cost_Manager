package il.ac.shenkar.proj.view;

import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.viewmodel.IViewModel;

public interface IView {

    //public void displayPieChart(Map map);
    public void setViewModel(IViewModel vm);
    public void showMessage(String text);
    public void showItems(CostItem[] vec);
    //..
}
