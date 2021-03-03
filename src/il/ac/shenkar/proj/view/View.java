package il.ac.shenkar.proj.view;

import il.ac.shenkar.proj.model.Category;
import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.CostManagerException;
import il.ac.shenkar.proj.model.Currency;
import il.ac.shenkar.proj.viewmodel.IViewModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * The View class is implementing the IView interface.
 *
 * It has two variables -
 *      IViewModel vm
 *      ApplicationUI ui
 *
 *  The vm var is the connection to the view-model object.
 *  The ui var is setting the view components and ui.
 *
 * */

public class View implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    @Override
    public void displayPieChart(JFreeChart map) {
        ui.displayPieChart(map);
    }

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showMessage(String text) {
        ui.showMessage(text);
    }

    @Override
    public void showItems(List<CostItem> vec) {
        ui.showItems(vec);
    }

    @Override
    public void showReport(List<CostItem> vec) {
        ui.showReport( vec);
    }

    @Override
    public void printCategories(List<Category> vec) {
        ui.printCategories(vec);
    }

    @Override
    public void printCategoriesTest(List<Category> vec) {
        ui.printCategoriesTest(vec);
    }


    /**
     *
     * Constructor to build the ui and than use start method to run the thread.
     *
     * */
    public View() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View.this.ui = new ApplicationUI();
            // Waiting for the thread from the pool of the ViewModel to bring the categories to the component before ui starts
                try{
                Thread.sleep(3000);
            }catch(InterruptedException err){
                System.out.println(err.getMessage());
            }
                View.this.ui.start();
            }
        });
    }
    /**
     * ApplicationUI - inner class that is the functionality of the gui,
     * holds all the components of the ui and implementing the Iview interface.
     *
     * Two main windows -
     * 1. The main window that shows all costs in he DB and has the options of adding the new cost item, deleting cost item by ID,
     * adding new category and go to the second srceen.
     * 2. The second screen will give the user the option to enter two dates and get the financial report between those dates.
     *
     * */
    public class ApplicationUI //implements IView
    {
        //
        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelBottom;
        private JPanel panelMain;
        private JPanel panelMessage;
        private JPanel panelLeftMessage;
        private JPanel panelMiddleMessage;
        private JPanel panelRightMessage;
        private JTextField tfItemSum;
        private JTextField tfItemCurrency;
        private JTextField tfItemDescription;
        private JTextField tfItemCategory;
        private JTextField tfMessage;
        private JTextField tfDelete;
        private JTextField tfAddcat;
        private JButton btAddCostItem;
        private JButton btGetReport;
        private JButton btDeleteCost;
        private JButton btCategory;
        private JButton btBack;
        private JButton btPie;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JLabel lbItemSum;
        private JLabel lbItemCategory;
        private JLabel lbAddCat;
        private JLabel lbItemCurrency;
        private JLabel lbItemDescription;
        private JLabel lbMessage;
        private JLabel lbId;
        private JLabel lbItemDate;
        private JScrollPane categoryScroll;

        private JFrame reportFrame;
        private JPanel topPanel;
        private JPanel textPanel;
        private JPanel piePanel;
        private JLabel from;
        private JLabel until;
        private JTextField tfItemDate;
        private JTextField fromtxt;
        private JTextField untiltxt;
        private JTextArea reporttxt;
        private JButton reportBtn;

        private JComboBox categoryBox;



        // private JFrame delFrame;
        // private JPanel delPanel;
        // private JLabel delLabel;
        // private JButton delBtn;


        public ApplicationUI() {
            //creating the MAIN window
            frame = new JFrame("CostManager");
            //creating the four panels
            panelMain = new JPanel();
            panelBottom = new JPanel();
            panelTop = new JPanel();
            panelMessage = new JPanel();
            panelLeftMessage = new JPanel();
            panelMiddleMessage = new JPanel();
            panelRightMessage = new JPanel();
            //creating the main ui components
            tfItemSum = new JTextField(8);
            tfItemCurrency = new JTextField(8);
            tfItemDescription = new JTextField(20);
            tfItemCategory = new JTextField(10);
            btAddCostItem = new JButton("Add Cost Item");
            btGetReport = new JButton("Get Report");
            btDeleteCost = new JButton("Delete Cost");
            btCategory = new JButton("Add Category");
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            lbItemCurrency = new JLabel("Item Currency:");
            lbItemDescription = new JLabel("Item Description:");
            lbItemCategory = new JLabel("Item Category:");
            lbItemSum = new JLabel("Item Sum:");
            lbId = new JLabel("ID:");
            lbItemDate = new JLabel("Date:");
            tfItemDate = new JTextField(10);
            lbAddCat = new JLabel("Category");
            //creating the messages ui components
            lbMessage = new JLabel("Message: ");
            tfMessage = new JTextField(30);
            tfMessage.setEditable(false);
            tfAddcat = new JTextField(10);
            tfDelete = new JTextField(10);

            vm.printCategories();


            // Creating GET REPORT window
            reportFrame = new JFrame("Get Report");
            textPanel = new JPanel();
            piePanel = new JPanel();
            topPanel = new JPanel();
            from = new JLabel("From");
            until = new JLabel("Until");
            fromtxt = new JTextField(10);
            untiltxt = new JTextField(10);
            reportBtn = new JButton("Get Report");
            reporttxt = new JTextArea(30, 95);
            btBack = new JButton("Back");
            btPie = new JButton("PieChart");
            reporttxt.setEditable(false);


        }

        public void start() {

            //adding the components to the top panel
            panelTop.add(lbItemSum);
            panelTop.add(tfItemSum);
            panelTop.add(lbItemDate);
            panelTop.add(tfItemDate);
            panelTop.add(lbItemCategory);
            panelTop.add(categoryBox);
            panelTop.add(lbItemDescription);
            panelTop.add(tfItemDescription);
            panelTop.add(lbItemCurrency);
            panelTop.add(tfItemCurrency);
            panelTop.add(btAddCostItem);

            //setting BorderLayout as the LayoutManager for panelMain
            panelMain.setLayout(new BorderLayout());

            //setting GridLayout 1x1 as the LayoutManager for panelBottom
            panelBottom.setLayout(new BorderLayout());

            //adding the components to the bottom panel
            panelBottom.add(scrollPane);

            //adding the components to the messages panel
            panelLeftMessage.add(btDeleteCost);
            panelLeftMessage.add(lbId);
            panelLeftMessage.add(tfDelete);
            panelMiddleMessage.add(btGetReport);
            panelMiddleMessage.add(lbMessage);
            panelMiddleMessage.add(tfMessage);
            panelRightMessage.add(btCategory);
            panelRightMessage.add(lbAddCat);
            panelRightMessage.add(tfAddcat);

            // adding panel to the bottom panel
            panelMessage.add(panelLeftMessage, BorderLayout.WEST);
            panelMessage.add(panelMiddleMessage, BorderLayout.CENTER);
            panelMessage.add(panelRightMessage, BorderLayout.EAST);

            //setting a different color for the panel message
            panelMessage.setBackground(Color.YELLOW);


            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //adding the two panels to the main panel
            //panelMain.add(panelTop);
            panelMain.add(panelBottom, BorderLayout.CENTER);

            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);

            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);

            //adding the message panel to the window
            frame.add(panelMessage, BorderLayout.SOUTH);


            //--------------- Start GET REPORT WINDOW --------------------------------------
            // adding the components to the pieChart panel

            //setting the topPanel
            topPanel.add(btPie);
            topPanel.add(btBack);
            topPanel.add(from);
            topPanel.add(fromtxt);
            topPanel.add(until);
            topPanel.add(untiltxt);
            topPanel.add(reportBtn);

            //setting BorderLayout as the LayoutManager for topPanel
            reportFrame.setLayout(new BorderLayout());

            //creating textPanel
            textPanel.add(reporttxt);


            //adding top panel and text panel to the report window
            reportFrame.add(topPanel, BorderLayout.NORTH);
            reportFrame.add(textPanel, BorderLayout.CENTER);
            reportFrame.add(piePanel, BorderLayout.SOUTH);


            //displaying the window
            reportFrame.setSize(1400, 1000);
            reportFrame.setVisible(false);
            // ---------------------- End of GET REPORT -------------------------------

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            // When closing the window of the get Report page it shows the main page again
            reportFrame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    reportFrame.setVisible(false);
                    frame.setVisible(true);
                }
            });
//
//             Switches between the main page to the get report page
            btGetReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    reportFrame.setVisible(true);

                }
            });

            btBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reportFrame.setVisible(false);
                    frame.setVisible(true);
                }
            });

            btPie.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String startDate = "";
                    String endDate = "";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                    try {
                        startDate = fromtxt.getText();
                        try {
                            dateFormat.parse(startDate.trim());
                        } catch (ParseException pe) {
                            View.this.showMessage("Date is not valid");
                            throw new CostManagerException("date is not valid");
                        }
                    } catch (NumberFormatException | CostManagerException ex) {
                        View.this.showMessage("Insert another date, " + ex.getMessage());
                    }
                    try {
                        endDate = untiltxt.getText();
                        try {
                            dateFormat.parse(endDate.trim());
                        } catch (ParseException pe) {
                            View.this.showMessage("Date is not valid");
                            throw new CostManagerException("date is not valid");
                        }
                    } catch (NumberFormatException | CostManagerException ex) {
                        View.this.showMessage("Insert another date, " + ex.getMessage());
                    }
                    vm.getPie(Date.valueOf(startDate), Date.valueOf(endDate));
                }
            });



            btCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String name = tfAddcat.getText();
                        if(name == null || name.length() == 0){
                            throw new CostManagerException("category cannot be empty");
                        }
                        Category cat = new Category(name);
                        vm.addCategory(cat);

                    } catch (CostManagerException costManagerException) {
                            costManagerException.printStackTrace();
                    }
                }
            });


            //handling cost item adding button click
            btAddCostItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        // get the description
                        String description = tfItemDescription.getText();
                        if (description == null || description.length() == 0) {
                            throw new CostManagerException("description cannot be empty");
                        }

                        // gets the sum
                        double sum = Double.parseDouble(tfItemSum.getText());
                        String currencyStr = tfItemCurrency.getText();
                        Currency currency = null;
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

                        // Get the category
                        String category = String.valueOf(categoryBox.getSelectedItem());
                        System.out.println(category);
                        // NOTE TO SELF - Needs to add checks to the category input
                        String costDate = "";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                        try {
                            costDate = tfItemDate.getText();
                            try {
                                dateFormat.parse(costDate.trim());
                            } catch (ParseException pe) {
                                System.out.println("Data is not valid!");
                                View.this.showMessage("Date is not valid");
                                throw new CostManagerException("date is not valid");
                            }
                        } catch (NumberFormatException | CostManagerException ex) {
                            System.out.println("Insert another date");
                            View.this.showMessage("Insert another date, " + ex.getMessage());
                        }
                        // Creates Cost item object and move it to the view-model object
                        CostItem item = new CostItem(Date.valueOf(costDate), category, description, sum, currency);
                        vm.addCostItem(item);


                    } catch (NumberFormatException ex) {
                        System.out.println("problem with entered sum... ");
                        View.this.showMessage("problem with entered sum... " + ex.getMessage());
                    } catch (CostManagerException ex) {
                        System.out.println("problem with entered data... ");
                        View.this.showMessage("problem with entered data... problem with description... " + ex.getMessage());
                    }
                }
            });

            // Delete button event listener
            btDeleteCost.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        double idToDelete = Double.parseDouble(tfDelete.getText());
                        vm.deleteCostItem((int) idToDelete);
                    } catch (NumberFormatException ex) {
                        System.out.println("problem with entered id... ");
                        View.this.showMessage("problem with entered id... " + ex.getMessage());
                    }
                }
            });

            // Get report button listenner
            reportBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String startDate = "";
                    String endDate = "";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                    try {
                        startDate = fromtxt.getText();
                        try {
                            dateFormat.parse(startDate.trim());
                        } catch (ParseException pe) {
                            View.this.showMessage("Date is not valid");
                            throw new CostManagerException("date is not valid");
                        }
                    } catch (NumberFormatException | CostManagerException ex) {
                        System.out.println("Insert another date ");
                        View.this.showMessage("Insert another date, " + ex.getMessage());
                    }
                    try {
                        endDate = untiltxt.getText();
                        try {
                            dateFormat.parse(endDate.trim());
                        } catch (ParseException pe) {
                            System.out.println("Data is not valid ");
                            View.this.showMessage("Date is not valid");
                            throw new CostManagerException("Date is not valid");
                        }
                    } catch (NumberFormatException | CostManagerException ex) {
                        System.out.println("Insert another date");
                        View.this.showMessage("Insert another date, " + ex.getMessage());
                    }
                    vm.getReport(Date.valueOf(startDate), Date.valueOf(endDate));
                }
            });
            //displaying the window
            frame.setSize(1400, 600);
            frame.setVisible(true);
        }

        public void printCategoriesTest(List<Category> categories) {
            DefaultComboBoxModel catList = new DefaultComboBoxModel(categories.toArray());
            categoryBox = new JComboBox(catList);
        }

        // Shows the user the informative message about the actions the he did in the screen
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tfMessage.setText(text);
                    }
                });

            }
        }

        public void displayPieChart(JFreeChart map) {
            ChartPanel chartP = new ChartPanel(map);
            piePanel.removeAll();
            if (SwingUtilities.isEventDispatchThread()) {
                piePanel.add(chartP, BorderLayout.CENTER);
                SwingUtilities.updateComponentTreeUI(reportFrame);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        piePanel.add(chartP, BorderLayout.CENTER);
                        SwingUtilities.updateComponentTreeUI(reportFrame);
                    }
                });
            }
        }


        public void showReport(List<CostItem> vec) {
            StringBuilder sb = new StringBuilder();
            for (CostItem item : vec) {
                sb.append(item.toString());
                sb.append("\n");
            }
            String text = sb.toString();

            if (SwingUtilities.isEventDispatchThread()) {
                textArea.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        reporttxt.setText(text);
                    }
                });

            }
        }

        // Shows all costs to the user from DB
        public void showItems(List<CostItem> items) {
            StringBuilder sb = new StringBuilder();
            for (CostItem item : items) {
                sb.append(item.toString());
                sb.append("\n");
            }
            String text = sb.toString();

            if (SwingUtilities.isEventDispatchThread()) {
                textArea.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(text);
                    }
                });

            }
        }


        public void printCategories(List<Category> vec) {
            DefaultComboBoxModel newList = new DefaultComboBoxModel();
            for(Category cat : vec){
                newList.addElement(cat);
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    categoryBox.setModel(newList);
                    tfAddcat.setText("");
                }
            });
        }
    }
}