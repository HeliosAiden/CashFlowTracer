/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cashflowtracer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author helios_aiden
 */
public class CashFlowTracer {

    // Variables for the main frame and UI components
    private JFrame frame;
    private JPanel titleBar;
    private JPanel dashboardPanel;
    private JPanel buttonsPanel;
    private JLabel titleLabel;
    private JLabel closeLabel;
    private JLabel minimizLabel;
    private JButton addTransactionButton;
    private JButton removeTransactionButton;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    // Varialbes to store the total amount
    private double totalAmount = 0.0;
    // ArrayList to store data panel values
    private ArrayList<String> dataPanelValues = new ArrayList<>(3);
    // Variables for form dragging
    private boolean isDragging = false;
    private Point mouseOffSet;
    private int rowID = 0;

    // Program starts from here
    public static void main(String[] args) {
        new CashFlowTracer();

    }

    // Constructor
    public CashFlowTracer(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);

        // remove form border and default close and minimize buttons
        frame.setUndecorated(true);

        // set custom border to frame
        frame.getRootPane().setBorder(new MatteBorder(0,0,1,1, new Color(52, 73, 94)));

        // create and setup title bar
        titleBar = new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(52, 73, 94));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));
        frame.add(titleBar, BorderLayout.NORTH);

        // create and setup title label
        titleLabel = new JLabel("Cash Flow Tracer");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setBounds(10,0,250,30);
        titleBar.add(titleLabel);

        // create and setup close label
        closeLabel = new JLabel("x");
        closeLabel.setForeground(Color.WHITE);
        closeLabel.setFont(new Font("Arial", Font.BOLD, 17));
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setBounds(frame.getWidth() - 50, 0, 30, 30);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // mouse listener for close label
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(Color.WHITE);
            }
        });
        titleBar.add(closeLabel);

        // create and setup minimize label
        minimizLabel = new JLabel("-");
        minimizLabel.setForeground(Color.WHITE);
        minimizLabel.setFont(new Font("Arial", Font.BOLD, 17));
        minimizLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimizLabel.setBounds(frame.getWidth() - 80, 0, 30, 30);
        minimizLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // mouse listener for minimize label
        minimizLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                minimizLabel.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                minimizLabel.setForeground(Color.WHITE);
            }
        });
        titleBar.add(minimizLabel);

        // mouse listener for window dragging
        titleBar.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                mouseOffSet = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        // mouse motion listener for motion draging
        titleBar.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    Point newLocation = e.getLocationOnScreen();
                    newLocation.translate(-mouseOffSet.x, -mouseOffSet.y);
                    frame.setLocation(newLocation);
                }
            }
        });


        // create and set up the dashboard panel
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        dashboardPanel.setBackground(Color.WHITE);
        frame.add(dashboardPanel, BorderLayout.CENTER);

        // add data panels for Expense, Income and Total
        addDataPanel("Expense", 0);
        addDataPanel("Income", 1);
        addDataPanel("Total", 2);

        // create and set up buttons panel

        addTransactionButton = new JButton("Add Transaction");
        addTransactionButton.setBackground(new Color(41,28,185));
        addTransactionButton.setForeground(Color.WHITE);
        addTransactionButton.setFocusPainted(false);
        addTransactionButton.setBorderPainted(false);
        addTransactionButton.setFont(new Font("Arial", Font.BOLD, 14));
        addTransactionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addTransactionButton.addActionListener((e) -> {
            showAddTransactionDialog();
        });

        removeTransactionButton = new JButton("Remove Transaction");
        removeTransactionButton.setBackground(new Color(231,76,60));
        removeTransactionButton.setForeground(Color.WHITE);
        removeTransactionButton.setFocusPainted(false);
        removeTransactionButton.setBorderPainted(false);
        removeTransactionButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeTransactionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout(10, 5));
        buttonsPanel.add(addTransactionButton, BorderLayout.NORTH);
        buttonsPanel.add(removeTransactionButton, BorderLayout.SOUTH);
        dashboardPanel.add(buttonsPanel);

        // set up transaction table
        String[] columneNames = {"ID", "Type", "Description", "Amount"};
        tableModel = new DefaultTableModel(columneNames, 0);

        transactionTable = new JTable(tableModel);
        configureTransactionTable();

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        configureScrollPane(scrollPane);
        dashboardPanel.add(scrollPane);


        frame.setVisible(true);
    }

    // Display new dialog when adding a new transition
    private void showAddTransactionDialog(){

        // Create JDialog for adding a transaction
        JDialog dialog = new JDialog(frame, "Add transaction", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(frame);

        // Create a Jpanel to hold the components in a grid layout
        JPanel dialogPanel = new JPanel(new GridLayout(4,0,10,10));
        dialogPanel.setBackground(Color.LIGHT_GRAY);
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Create & configure components for transaction input
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{ "Income", "Expends"});
        typeComboBox.setBackground(Color.WHITE);
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionTextField = new JTextField();

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountTextField = new JTextField();

        // Style add button
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(41,128,185));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // currently debugging addTransactionButton

        addButton.addActionListener((e) -> {
            addTransaction(typeComboBox, descriptionTextField, amountTextField);
            dialog.setVisible(false);
        });

        // Add components to dialog panel
        dialogPanel.add(typeLabel);
        dialogPanel.add(typeComboBox);
        dialogPanel.add(descriptionLabel);
        dialogPanel.add(descriptionTextField);
        dialogPanel.add(amountLabel);
        dialogPanel.add(amountTextField);
        dialogPanel.add(new JLabel()); // Empty label for spacing
        dialogPanel.add(addButton);

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    // Add new transaction
    private void addTransaction(JComboBox<String> typeComboBox, JTextField descriptionField, JTextField amountField) {
        String type = (String) typeComboBox.getSelectedItem();
        String description = descriptionField.getText();
        String amount = amountField.getText();

        // Parse amount String to a double value
        double newAmount = Double.parseDouble(amount.replace("$","").replace(" ","").replace(",",""));

        // Update the total amount based on the transaction type
        if (type.equals("Income")){
            totalAmount += newAmount;
        } else {
            totalAmount -= newAmount;
        }

        // Update the total amount displayed on dashboard
        JPanel totalPanel = (JPanel) dashboardPanel.getComponent(2);
        totalPanel.repaint();

        // determine the index of the data panel to update based on the transaction type
        int indexToUpdate = type.equals("Income") ? 1 : 0;


        // retrieve the current value of data panel
        String currentValue = dataPanelValues.get(indexToUpdate);

        // parse current amount string to double value
        double currentAmount = Double.parseDouble(currentValue.replace("$","").replace(" ","").replace(",",""));

        // calculate the updated amount based on the transaction type

        double updatedAmount = currentAmount + (type.equals("Income") ? newAmount : -newAmount);

        // update the data panel with new amount

        dataPanelValues.set(indexToUpdate, String.format("$%,.2f", updatedAmount));
        JPanel dataPanel = (JPanel) dashboardPanel.getComponent(indexToUpdate);
        dataPanel.repaint();

        // Should Connection class be here ready for db connection
        // Make sure to have try catch statement
        // *
        // *
        // *

        DefaultTableModel table = (DefaultTableModel) transactionTable.getModel();
        Transaction transaction = new Transaction(rowID++, type, description, newAmount);
        table.addRow(new Object[]{transaction.getId(), transaction.getType(), transaction.getDescription(), transaction.getAmount()});

    }

    // Configure the appearance and behavior of the transaction table
    private void configureTransactionTable() {

        transactionTable.setBackground(new Color(236,240,241));
        transactionTable.setRowHeight(30);
        transactionTable.setShowGrid(false);
        transactionTable.setBorder(null);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader tableHeader = transactionTable.getTableHeader();
        tableHeader.setForeground(Color.RED);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 22));
        tableHeader.setDefaultRenderer(new GradientHeaderRenderer());
    }

    private void configureScrollPane(JScrollPane scrollPane) {
        scrollPane.setPreferredSize(new Dimension(750,300));
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    // Draw a data panel with specified title and value
    private void drawDataPanel(Graphics g, String title, String value, int width, int height) {

        // draw panel
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255,255,255));
        g2d.fillRoundRect(0, 0, width, height, 20, 20);
        g2d.setColor(new Color(236,240,241));
        g2d.fillRect(0, 0, width, 40);

        // draw title
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString(title, 20, 30);

        // draw value
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(value, 20, 75);

    }

    // Add data panel to the dashboard panel
    private void addDataPanel(String title, int index){
        dataPanelValues.add("00");

        JPanel dataPanel = new JPanel(){

            // Override paintComponent method to customize appearance
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (title.equals("Total")) {
                    drawDataPanel(g, title, String.format("$%,.2f", totalAmount), getWidth(), getHeight());
                } else {
                    drawDataPanel(g, title, dataPanelValues.get(index), getWidth(), getHeight());
                }
            }

        };

        dataPanel.setLayout(new GridLayout(2,1));
        dataPanel.setPreferredSize(new Dimension(170, 100));
        dataPanel.setBackground(new Color(255,255,255));
        dataPanel.setBorder(new LineBorder(new Color(149, 165,166), 2));
        dashboardPanel.add(dataPanel);
    }

}


// Custom table header renderer with gradient background
class GradientHeaderRenderer extends JLabel implements TableCellRenderer{

    // private final Color startColor = new Color(192,192,192);
    // private final Color endColor = new Color(50,50,50);

    private final Color startColor = Color.BLUE;
    private final Color endColor = Color.CYAN;

    public GradientHeaderRenderer() {
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 22));
        setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0,0,1,1,Color.BLUE), BorderFactory.createEmptyBorder(2,5,2,5) ));
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, width, 0, endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);

        super.paintComponent(g);
    }
}

// create a custom scrollbar

class CustomScrollBarUI extends BasicScrollBarUI {

    private final Color thumbColor = new Color(189,195,199);
    private final Color trackColor = new Color(236,240,241);

    @Override
    protected void configureScrollBarColors() {
        super.configureScrollBarColors();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createEmptyButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createEmptyButton();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds){
        g.setColor(thumbColor);
        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds){
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    private JButton createEmptyButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0,0));
        button.setMaximumSize(new Dimension(0,0));
        button.setMinimumSize(new Dimension(0,0));
        return button;
    }

}