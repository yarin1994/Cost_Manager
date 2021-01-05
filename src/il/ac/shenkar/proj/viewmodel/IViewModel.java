package il.ac.shenkar.proj.viewmodel;

import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.IModel;
import il.ac.shenkar.proj.view.IView;

public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);
    public void addCostItem(CostItem item);

}
