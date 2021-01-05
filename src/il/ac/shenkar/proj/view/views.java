//package il.ac.shenkar.proj.view;
//import javax.swing.*;
//import java.awt.*;
//
//public class view {
////    MAIN FRAME
//    private JFrame mainFrame;
//    private JPanel mainPanel;
//    private JLabel categoryLabel;
////    private JLabel mainLabel;
//    private JButton addCost;
//    private JButton getReport;
//
////    ADD COST FRAME
//    private JFrame addFrame;
//    private JPanel topPanel;
//    private JPanel midPanel;
//    private JLabel sumLabel;
//    private JLabel currencyLabel;
//    private JMenuItem menu;
//    private JButton plusBtn;
//    private JButton addSum;
//    private JTextArea description;
//    private JTextField sum;
//    private JTextField currency;
//    private JButton plus;
//
//
//
//
//    public void init(){
////        MAIN WINDOW
//        mainFrame = new JFrame("Cost Item");
//        mainFrame.setSize(400, 400);
//
//        mainPanel = new JPanel();
//        addCost = new JButton("Add Cost");
//        getReport = new JButton("Get Report");
//
//        mainPanel.add(addCost);
//        mainPanel.add(getReport);
//        mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//        mainFrame.setVisible(false);
//
////        ADD COST WINDOW
//            addFrame = new JFrame();
//            addFrame.setSize(400,400);
////        Top Panel
//        topPanel = new JPanel();
//        plus = new JButton("+");
//        plus.setPreferredSize(new Dimension(15, 15));
//        categoryLabel = new JLabel("Category");
//        menu = new JMenuItem();
//        topPanel.add(categoryLabel);
//        topPanel.add(menu);
//        topPanel.add(plus);
//
//
////        Mid Panel
//        midPanel = new JPanel();
//        sum = new JTextField(4);
//        currency = new JTextField(4);
//        sumLabel = new JLabel("Sum");
//        currencyLabel = new JLabel("Currency");
//        description = new JTextArea();
//        description.setEditable(true);
//
////        Text Panel
//        
//
//        midPanel.add(sumLabel);
//        midPanel.add(sum);
//        midPanel.add(currencyLabel);
//        midPanel.add(currency);
//        midPanel.add(description);
//
////        Lower Panel
//
//
//
//
//        addFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
//        addFrame.getContentPane().add(BorderLayout.CENTER, midPanel);
//        addFrame.setVisible(true);
//
//
//
//
//    }
//
//    public static void main(String args[]){
//        view v = new view();
//        v.init();
//    }
//
//
//
//
//}
