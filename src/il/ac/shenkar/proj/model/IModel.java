package il.ac.shenkar.proj.model;

import java.sql.Date;

public interface IModel {
//   DB CREATION AND KILL:
     public void createConnections() throws CostManagerException;
     public void killDB() throws CostManagerException;

//   COSTITEM FUNCTIONS:
     public void addCostItem(CostItem item) throws CostManagerException;
     public void printCostItem() throws  CostManagerException;
     public void getReport(Date start, Date end) throws CostManagerException;
     public void deleteCostItem(int id) throws CostManagerException;

//   CATEGORY FUNCTIONS:
     public void addCategory(Category category) throws CostManagerException;
     public void printCategories() throws  CostManagerException;
     public void deleteCategory(String category) throws CostManagerException;

     }
