/**
 * @author Yahli Sofer - 205701212
 * @author Yarin Mizrahi - 205663917
 * */

package il.ac.shenkar.proj.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class DerbyDBModel implements IModel {
    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String connectionString = "jdbc:derby:";

    /**
     * createTable function:
     * in this function we have two queries which we used in order to open our tables.
     * we have total of 2 tables: CostItem and Categories.
     * @throws CostManagerException
     */
    public void createTable() throws CostManagerException{
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "";
        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
//            query = "CREATE TABLE CostItem(id int GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1) not null ,  date Date, category varchar(50), description varchar (256) , summary double, currency varchar (6))";
//            query = "create table categories(name varchar(25) primary key )";
//            query = "insert into categories values 'Food'";
//            statement.execute("DROP TABLE Categories");
//            statement.execute(query);
            killDB(connection,statement, rs);
        }catch (CostManagerException | SQLException err){
            System.out.println(err.getMessage());
            throw new CostManagerException("Could not open DB", err);
        }
    }


    /**
     * C'tor
     * calles createConnections and forName one time in this program runtime.
     * @throws CostManagerException
     */
    public DerbyDBModel() throws CostManagerException {
        createConnections();
    }

    /**
     * In this function we create a list which will hold all of the costItems details.
     * the data will be pulled out of our data base.
     * in this function we have a while loop which its goal is to go through all of our elements and save them on the list.
     * @return - the return value is a list of all the costItems we have on the DB.
     * @throws CostManagerException
     */
    public List<CostItem> getCostItems() throws CostManagerException {
        List<CostItem> costItems = new ArrayList<CostItem>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM CostItem");
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                Double sum = rs.getDouble("summary");
                String currencyStr = rs.getString("currency");
                String category = rs.getString("category");
                String description = rs.getString("description");
                Currency currency;
                switch (currencyStr) {
                    case "EURO":
                        currency = Currency.EURO;
                        break;
                    case "ILS":
                        currency = Currency.ILS;
                        break;
                    case "GBP":
                        currency = Currency.GBP;
                        break;
                    default:
                        currency = Currency.USD;
                }
                //Build the List<CostItems>
                costItems.add(new CostItem(id,date,category,description, sum,currency));
            }
            killDB(connection,statement,rs);
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException(e.getMessage());
            }
        }
        return costItems;
    }


    /**
     * In this function we call forName and creating our connectin.
     * this function called once in our program.
     * @throws CostManagerException
     */
    public void createConnections() throws CostManagerException {
        try {
            Class.forName(driver);
            System.out.println("In createConnection\n");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            throw new CostManagerException("Could not open connection to DB", e);
        }
    }


    /**
     * This function is used for us to push new costItems to our data base.
     * @param item - all of the inputs we sent from the application when entering a new costItem are sent throug item.
     * @throws CostManagerException
     */
    @Override
    public void addCostItem(CostItem item) throws CostManagerException {

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement state;
        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            state = connection.prepareStatement("insert into CostItem(date,category,description,summary,currency) values (?,?,?,?,?)");
            
            state.setDate(1, item.getDate());
            state.setString(2, item.getCategories());
            state.setString(3, item.getDescription());
            state.setDouble(4, item.getSum());
            state.setString(5, item.getCurrency());
            state.execute();

            killDB(connection,statement,rs);
        } catch (SQLException e) {
            throw new CostManagerException("problem with adding cost item ", e);
        }
    }

    /**
     * This function pritns all of the costItems that we have on our costItem table.
     * @throws CostManagerException
     */
    public void printCostItem() throws  CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM CostItem");
            while (rs.next()) {
                System.out.println("id: " + rs.getInt(1));
                System.out.println("date: " + rs.getDate(2));
                System.out.println("category: " + rs.getString(3));
                System.out.println("description: " + rs.getString(4));
                System.out.println("sum : " + rs.getDouble(5));
                System.out.println("currency : " + rs.getString(6));
                System.out.println('\n');
                
                killDB(connection,statement,rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new CostManagerException("Could not print cost item", throwables);
        }
    }

    /**
     * This function refers to the get report button which gives us the data of costItems between two dates.
     * @param start - the start date which we want to get costItems from
     * @param end - the end date of the dates we want to get costItmes.
     * @return - the return value is  a list of all the costItems who happend between the dates listed.
     * @throws CostManagerException
     */
    public List<CostItem> getReport(Date start, Date end) throws CostManagerException{
        List<CostItem> result = new ArrayList<CostItem>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM CostItem WHERE date BETWEEN DATE ('" + start.toLocalDate() + "') AND DATE('" + end.toLocalDate() + "')");
            while(rs.next()){
                result.add(new CostItem((rs.getInt(1)),(rs.getDate(2)), rs.getString(3), rs.getString(4), rs.getDouble(5), Currency.valueOf(rs.getString(6))));
            }
            for(int i = 0; i < result.size(); i++){
                System.out.println(result.get(i));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new CostManagerException("Could not get report ", throwables);
        }
        killDB(connection,statement,rs);
        return result;
    }

    /**
     * the pieChart functions prints a dataset who contains all of the costItems between the dates we listed and those
       costItems grouped by categories.
     * @param start - the start date which we want to get costItems from
     * @param end - the end date of the dates we want to get costItmes.
     * @return - the return value is the chart which we created based on two dates.
     * @throws CostManagerException
     */
    public JFreeChart pieChart(Date start, Date end) throws CostManagerException{
        JFreeChart chart = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT category, SUM(summary) as total FROM CostItem WHERE date BETWEEN DATE ('" + start.toLocalDate() + "') AND DATE('" + end.toLocalDate() + "') group by category");
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (rs.next()) {
                dataset.setValue(rs.getString("category"), Double.parseDouble(rs.getString("total")));
            }
            chart = ChartFactory.createPieChart("Category - amounts", dataset, true, true, false);
            int width = 560;
            int height = 370;
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new CostManagerException("Could not show piechart", throwables);
        }
        finally {
            if(rs != null) try {
                rs.close();
            }catch (SQLException throwables){
                throw new CostManagerException(throwables.getMessage());
            }
        }
        return chart;
    }

    /**
     * this function prints the pieChart dataset on the getReport page.
     * @param result - holds all of the categories listed and their total amount in precentage.
     * @throws CostManagerException
     */
    public void printPiechart(ResultSet result) throws  CostManagerException{
//        for(int i=0; i<result.size(); i++)
        while (true){
            try {
                System.out.println("category: " + result.getString("category") + " total: " + result.getDouble("total"));
                if (!result.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new CostManagerException("Could not print piechart", throwables);
            }
        }
        System.out.println('\n');
    }

    /**
     * This function deletes costItem by id.
     * @param id - uniqe id of costItem.
     * @throws CostManagerException
     */
    public void deleteCostItem(int id) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "";
        try{
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            query = "delete from CostItem where id = "+ id;
            statement.execute(query);
            System.out.println("Successfully deleted "+ id);
            killDB(connection,statement,rs);
        }
        catch (SQLException e){
            throw new CostManagerException("Problem deleting cost-item", e);
        }
    }

    /**
     * This function gets the name of the new category and pushes it to the categories table.
     * @param category - a string holds the new category name.
     * @throws CostManagerException
     */
    public void addCategory(Category category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement state;
        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            state = connection.prepareStatement("insert into categories(name) values (?)");
            state.setString(1, category.getCategory());
            state.execute();
            killDB(connection,statement,rs);
        } catch (SQLException e) {
            throw new CostManagerException("problem with adding cost item ", e);
        }
    }

    /**
     * This function gets all of the categories out of the categories table.
     * @return - a list of all the categories exist on the table.
     * @throws CostManagerException
     */
    public List<Category> printCategories() throws  CostManagerException{
        List<Category> categories = new ArrayList<Category>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            //getting connection by calling get connection
            connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM categories");
            while (rs.next()){
                String name = rs.getString("name");
                categories.add(new Category(name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new CostManagerException("Could not print categories", throwables);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException(e.getMessage());
            }
            killDB(connection,statement,rs);
        }
        return categories;
    }

    /**
     * This function kills the connection to the DB.
     * @param connection
     * @param statement
     * @param rs
     * @throws CostManagerException
     */
    public void killDB(Connection connection, Statement statement, ResultSet rs) throws CostManagerException {
        if (statement != null) try {
            statement.close();
        } catch (Exception e) {
        }
        if (connection != null) try {
            connection.close();
        } catch (SQLException e) {
            throw new CostManagerException("Could not killDB", e);
        }
        if (rs != null) try {
            rs.close();
        } catch (Exception e) {
            throw new CostManagerException("Could not killDB", e);
        }
    }
}
