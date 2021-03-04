package il.ac.shenkar.proj;

import il.ac.shenkar.proj.model.CostManagerException;
import il.ac.shenkar.proj.model.IModel;
import il.ac.shenkar.proj.model.DerbyDBModel;
import il.ac.shenkar.proj.view.IView;
import il.ac.shenkar.proj.view.View;
import il.ac.shenkar.proj.viewmodel.IViewModel;
import il.ac.shenkar.proj.viewmodel.ViewModel;

public class Application {
    public static void main(String args[]) {

        //creating the application components
        IModel model = null;
        try {
            model = new DerbyDBModel();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        IView view = new View();
        IViewModel vm = new ViewModel();
    
        //connecting the components with each other
        view.setViewModel(vm);
        vm.setModel(model);
        vm.setView(view);
    }
}