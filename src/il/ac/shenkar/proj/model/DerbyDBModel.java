package il.ac.shenkar.proj.model;

import  il.ac.shenkar.proj.model.CostManagerException;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

public class DerbyDBModel implements IModel {
    public static String driver = "org.apache.derby.jdbc.ClientDriver";
    public static String protocol = "jdbc:derby://localhost:1527/gagamoDB;create=true";

    public void DerbyDBModel() throws CostManagerException {
        startDerbyDB();
    }

    public void startDerbyDB() throws CostManagerException {
        try {

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addCostItem(CostItem item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try{
            Class.forName(driver);
            //getting connection by calling get connection
            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();
            String query;
//          query = "CREATE TABLE CostItem (id varchar (10) , date Date, category String, description varchar (256) , summary double, currency varchar (6))";
//            statement.execute(query);
//            statement.execute("DROP TABLE CostItem");
//            statement.execute("insert into CostItem (description , sum , currency)");
//            String query = "insert into CostItem (description, summary, currency) values (?,?,?,?,?,?)";
//            PreparedStatement state = connection.prepareStatement(query);
//            state.setString(1, "green ");
//            state.setDouble(2, 100);
//            state.setString(3, "EURO ");
//            state.execute();

//            statement.execute(query);
            rs = statement.executeQuery("SELECT * FROM CostItem");
            while (rs.next()){
                System.out.println("id " + rs.getString(item.getId()) + " date "  + rs.getDate(String.valueOf(item.getDate())) + " category " + rs.getString(item.getCategory()) + " description "
                        + rs.getString(item.getDescription()) + " summary " + rs.getString((int) item.getSum()) + " currency " + rs.getString(item.getCurrency()));

            }

        } catch(SQLException | ClassNotFoundException e) {
            throw new CostManagerException("problem with adding cost item",e);
//            throw new CostManagerException("oh shit");
        }finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
            }
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
        }



    }

//    @Override
    public void deleteCostItem(CostItem item) throws CostManagerException {
    }

    //...
}
