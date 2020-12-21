package il.ac.shenkar.proj.model;

import  il.ac.shenkar.proj.model.CostManagerException;


import java.sql.*;
import java.util.ArrayList;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

public class DerbyDBModel implements IModel {
    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String connectionString = "jdbc:derby:";
    public Connection connection = null;
    public Statement statement = null;
    public ResultSet rs = null;
    public String query = "";
    public PreparedStatement state;
    public PreparedStatement categoryState;

    public DerbyDBModel() throws CostManagerException {
        createConnections();
    }
//    public void DerbyDBModel() throws CostManagerException {
//        startDerbyDB();
//    }


    public void createConnections() throws CostManagerException {
        try {
            Class.forName(driver);
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
//            this.connection = DriverManager.getConnection(protocol);
            System.out.println("Connected to DB");
            this.statement = this.connection.createStatement();
            query = "CREATE TABLE CostItem(id int GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1) not null , date Date, category varchar(50), description varchar (256) , summary double, currency varchar (6))";
//            query = "create table categories(name varchar(15))";
            statement.execute(query);
//            statement.execute("DROP TABLE CostItem");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addCostItem(CostItem item) throws CostManagerException {


        try {

            state = connection.prepareStatement("insert into CostItem(date,category,description,summary,currency) values (?,?,?,?,?)");

            state.setDate(1, item.getDate());
            state.setString(2, item.getCategory());
            state.setString(3, item.getDescription());
            state.setDouble(4, item.getSum());
            state.setString(5, item.getCurrency());
            state.execute();

            //statement.execute(query);


        } catch (SQLException e) {
            throw new CostManagerException("problem with adding cost item ", e);
        }
    }

    public void printCostItem() throws  CostManagerException {
        try {
            rs = statement.executeQuery("SELECT * FROM CostItem");
            while (rs.next()) {
                System.out.println("id: " + rs.getInt(1));
                System.out.println("date: " + rs.getDate(2));
                System.out.println("category: " + rs.getString(3));
                System.out.println("description: " + rs.getString(4));
                System.out.println("sum : " + rs.getDouble(5));
                System.out.println("currency : " + rs.getString(6));

//

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void getReport(Date start, Date end) throws CostManagerException{
        try {

            ArrayList<CostItem> result = new ArrayList<CostItem>();
            rs = statement.executeQuery("SELECT * FROM CostItem WHERE date BETWEEN DATE ('" + start.toLocalDate() + "') AND DATE('" + end.toLocalDate() + "')");
            while(rs.next()){
                result.add(new CostItem((rs.getDate(2)), rs.getString(3), rs.getString(4), rs.getDouble(5), Currency.valueOf(rs.getString(6))));
            }
            for(int i = 0; i < result.size(); i++){
                System.out.println(result.get(i));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteCostItem(int id) throws CostManagerException {

        try{
            query = "delete from CostItem where id = "+ id;
            statement.execute(query);
            System.out.println("Successfully deleted "+ id);
        }
        catch (SQLException e){
            throw new CostManagerException("Problem deleting cost-item", e);
        }
    }

    public void addCategory(Category category) throws CostManagerException {
        try {

            state = connection.prepareStatement("insert into categories(name) values (?)");

            state.setString(1, category.getCategory());
            state.execute();

            //statement.execute(query);


        } catch (SQLException e) {
            throw new CostManagerException("problem with adding cost item ", e);
        }

    }

    public void printCategories() throws  CostManagerException{
        try {
            rs = statement.executeQuery("SELECT  * FROM categories");

            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void deleteCategory(String category) throws CostManagerException{
        try{
            query = "delete from categories where name = "+ "'" + category + "'";
            statement.execute(query);
            System.out.println("Successfully deleted "+  category);
        }
        catch (SQLException e){
            throw new CostManagerException("Problem deleting category", e);
        }
    }

    public void killDB() throws CostManagerException {
        if (statement != null) try {
            statement.close();
        } catch (Exception e) {
        }
        if (connection != null) try {
            this.connection.close();
        } catch (SQLException e) {
        }
        if (rs != null) try {
            rs.close();
        } catch (Exception e) {
        }
    }

    //    @Override

}
