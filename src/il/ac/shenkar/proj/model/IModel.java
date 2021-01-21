package il.ac.shenkar.proj.model;

import org.jfree.chart.JFreeChart;

import java.sql.Date;
import java.util.List;

public interface IModel {
//   DB CREATION AND KILL:
     public void createConnections() throws CostManagerException;
     public void killDB() throws CostManagerException;

//   COSTITEM FUNCTIONS:
     public void addCostItem(CostItem item) throws CostManagerException;
     public void printCostItem() throws  CostManagerException;
     public List<CostItem> getReport(Date start, Date end) throws CostManagerException;
     public void deleteCostItem(int id) throws CostManagerException;
     public List<CostItem> getCostItems() throws CostManagerException;

//   CATEGORY FUNCTIONS:
     public void addCategory(Category category) throws CostManagerException;
     public List<Category> printCategories() throws  CostManagerException;
//     public void deleteCategory(String category) throws CostManagerException;
     public JFreeChart pieChart(Date start, Date end) throws CostManagerException;




}
