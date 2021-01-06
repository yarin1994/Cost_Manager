package il.ac.shenkar.proj.view;

import il.ac.shenkar.proj.model.Category;
import il.ac.shenkar.proj.model.CostItem;
import il.ac.shenkar.proj.model.CostManagerException;
import il.ac.shenkar.proj.model.Currency;
import il.ac.shenkar.proj.viewmodel.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showMessage(String text) {
        ui.showMessage(text);
    }

    @Override
    public void showItems(CostItem[] vec) {
        ui.showItems(vec);
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
        private JButton btbCategory;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JLabel lbItemSum;
        private JLabel lbItemCategory;
        private JLabel lbAddCat;
        private JLabel lbItemCurrency;
        private JLabel lbItemDescription;
        private JLabel lbMessage;
        private JLabel lbId;
        private JList<String> list;
        private JScrollPane categoryScroll;

        private JFrame reportFrame;
        private JPanel topPanel;
        private JPanel textPanel;
        private JLabel from;
        private JLabel until;
        private JTextField fromtxt;
        private JTextField untiltxt;
        private JTextArea reporttxt;
        private JButton reportBtn;

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
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            lbItemCurrency = new JLabel("Item Currency:");
            lbItemDescription = new JLabel("Item Description:");
            lbItemCategory = new JLabel("Item Category:");
            lbItemSum = new JLabel("Item Sum:");
            lbId = new JLabel("ID:");
            lbAddCat = new JLabel("Category");
            //creating the messages ui components
            lbMessage = new JLabel("Message: ");
            tfMessage = new JTextField(30);
            tfMessage.setEditable(false);
            tfAddcat = new JTextField(10);
            tfDelete = new JTextField(10);
            DefaultListModel<String> l1 = new DefaultListModel<>();
            // NOTE TO SELF - Will be added automatically from DB
            l1.addElement("C");
            l1.addElement("C++");
            l1.addElement("Java");
            l1.addElement("PHP");
            list = new JList(l1);
            list.setBounds(100,50, 75,40);
            categoryScroll = new JScrollPane(list);
            categoryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            btbCategory = new JButton("Add Category");



            // Creating GET REPORT window
            reportFrame = new JFrame("Get Report");
            textPanel = new JPanel();
            topPanel = new JPanel();
            from = new JLabel("From");
            until = new JLabel("Until");
            fromtxt = new JTextField(10);
            untiltxt = new JTextField(10);
            reportBtn = new JButton("Get Report");
            reporttxt = new JTextArea(30,95);
            reporttxt.setEditable(false);

        }

        public void start() {

            //adding the components to the top panel
            panelTop.add(lbItemSum);
            panelTop.add(tfItemSum);
            panelTop.add(lbItemCategory);
            panelTop.add(categoryScroll);
            //panelTop.add(list);
            panelTop.add(lbItemDescription);
            panelTop.add(tfItemDescription);
            panelTop.add(lbItemCurrency);
            panelTop.add(tfItemCurrency);
            panelTop.add(btAddCostItem);

            //setting BorderLayout as the LayoutManager for panelMain
            panelMain.setLayout(new BorderLayout());

            //setting GridLayout 1x1 as the LayoutManager for panelBottom
            // panelBottom.setLayout(new GridLayout(1, 1));
            panelBottom.setLayout(new BorderLayout());
            // panelLeftMessage.setLayout(new BorderLayout());
            // panelRightMessage.setLayout(new BorderLayout());

            //adding the components to the bottom panel
            panelBottom.add(scrollPane);

            //adding the components to the messages panel
            panelLeftMessage.add(btDeleteCost);
            panelLeftMessage.add(lbId);
            panelLeftMessage.add(tfDelete);
            panelMiddleMessage.add(btGetReport);
            panelMiddleMessage.add(lbMessage);
            panelMiddleMessage.add(tfMessage);
            panelRightMessage.add(btbCategory);
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


            //displaying the window
            reportFrame.setSize(1200, 600);
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

            // Switches between the main page to the get report page
            btGetReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    reportFrame.setVisible(true);

                }
            });


            //handling cost item adding button click
            btAddCostItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        // get the description
                        String description = tfItemDescription.getText();
                        if(description==null || description.length()==0) {
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
                        String category = tfItemCategory.getText();
                        // NOTE TO SELF - Needs to add checks to the category input

                        // Creates Cost item object and move it to the view-model object
                        CostItem item = new CostItem(category,description, sum, currency);
                        vm.addCostItem(item);


                    } catch (NumberFormatException ex) {
                        View.this.showMessage("problem with entered sum... "+ex.getMessage());
                    } catch(CostManagerException ex){
                        View.this.showMessage("problem with entered data... problem with description... "+ex.getMessage());
                    }
                }
            });

            //displaying the window
            frame.setSize(1200, 600);
            frame.setVisible(true);
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
            //@Override
            //public void setViewModel(IViewModel vm) {
            //
            //}
        }

        // Shows all costs to the user from DB
        public void showItems(CostItem[] items) {
            StringBuilder sb = new StringBuilder();
            for(CostItem item : items) {
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
    }
}