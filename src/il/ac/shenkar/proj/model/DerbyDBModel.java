package il.ac.shenkar.proj.model;

import javax.xml.namespace.QName;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class DerbyDBModel implements IModel {
    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String connectionString = "jdbc:derby:";
    public Connection connection = null;
    public Statement statement = null;
    public ResultSet rs = null;
    public String query = "";
    public PreparedStatement state;
    public PreparedStatement categoryState;
    ArrayList<pieChart> result = new ArrayList<pieChart>();


    public DerbyDBModel() throws CostManagerException {
        createConnections();
    }

    public List<CostItem> getCostItems() throws CostManagerException {
        List<CostItem> costItems = new ArrayList<CostItem>();

        try {
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



    public void createConnections() throws CostManagerException {
        try {
            Class.forName(driver);
            //getting connection by calling get connection
            System.out.println("Hello!");
            this.connection = DriverManager.getConnection(connectionString + "CostManagerDB;create=true");

//            this.connection = DriverManager.getConnection(protocol);
            System.out.println("Connected to DB\n");
            this.statement = this.connection.createStatement();
//            query = "CREATE TABLE CostItem(id int GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1) not null ,  date Date, category varchar(50), description varchar (256) , summary double, currency varchar (6))";
//            query = "create table categories(name varchar(25) primary key )";
//            query = "insert into categories values 'Food'";
//            statement.execute("DROP TABLE Categories");
//                        statement.execute(query);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addCostItem(CostItem item) throws CostManagerException {


        try {

            state = this.connection.prepareStatement("insert into CostItem(date,category,description,summary,currency) values (?,?,?,?,?)");

            state.setDate(1, item.getDate());
            state.setString(2, item.getCategories());
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
                System.out.println('\n');



            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public List<CostItem> getReport(Date start, Date end) throws CostManagerException{
        List<CostItem> result = new ArrayList<CostItem>();
        try {
            rs = statement.executeQuery("SELECT * FROM CostItem WHERE date BETWEEN DATE ('" + start.toLocalDate() + "') AND DATE('" + end.toLocalDate() + "')");
            while(rs.next()){
                result.add(new CostItem((rs.getInt(1)),(rs.getDate(2)), rs.getString(3), rs.getString(4), rs.getDouble(5), Currency.valueOf(rs.getString(6))));
            }
            for(int i = 0; i < result.size(); i++){
                System.out.println(result.get(i));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public void pieChart(Date start, Date end) throws CostManagerException{
        try {

            rs = statement.executeQuery("SELECT category, SUM(summary) as total FROM CostItem WHERE date BETWEEN DATE ('" + start.toLocalDate() + "') AND DATE('" + end.toLocalDate() + "') group by category");
//            while(rs.next()){
//                String categoryName = rs.getString("category");
//                Double tot = rs.getDouble("total");
//
//                result.add(new pieChart(categoryName, tot));
//            }
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (rs.next()) {
                dataset.setValue(rs.getString("name"), Double.parseDouble(rs.getString("amount")));
            }
            JFreeChart chart = ChartFactory.createPieChart("Category - amounts", dataset, true, true, false);
            int width = 560;
            int height = 370;
//            File pieChart = new File("Pie_Chart.jpeg");
//            ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if(rs != null) try {
                rs.close();
            }catch (SQLException throwables){
                throw new CostManagerException(throwables.getMessage());
            }
        }
    }

    public void printPiechart() throws  CostManagerException{

        for(int i=0; i<result.size(); i++){
            System.out.println("category: " + result.get(i).getCategoryName() + " total: " + result.get(i).getTotal());
        }
        System.out.println('\n');

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

        } catch (SQLException e) {
            throw new CostManagerException("problem with adding cost item ", e);
        }
    }

    public List<Category> printCategories() throws  CostManagerException{
        List<Category> categories = new ArrayList<Category>();


        try {
            rs = statement.executeQuery("SELECT * FROM categories");
            while (rs.next()){
//                System.out.println(rs.getString(1));

                String name = rs.getString("name");
//                System.out.println(name);



                categories.add(new Category(name));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException(e.getMessage());
            }
        }

        return categories;
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

//    public void getPieChart(Date start, Date end) throws CostManagerException{
//        try {
//            state = connection.prepareStatement("Select * from CostItem WHEN ");
//            state.setString(1, category.getCategory());
//            state.execute();
//
//        } catch (SQLException e) {
//            throw new CostManagerException("problem with adding cost item ", e);
//        }
//    }

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
}
