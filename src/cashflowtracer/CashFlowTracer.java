/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cashflowtracer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
    private ArrayList<String> dataPanelValues = new ArrayList<>();
    // Variables for form dragging
    private boolean isDragging = false;
    private Point mouseOffSet;
    
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
        frame.getRootPane().setBorder(new MatteBorder(5,5,5,5, new Color(52, 73, 94)));

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
        tableModel = new DefaultTableModel(columneNames, 20);
        transactionTable = new JTable(tableModel);
        configureTransactionTable();

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        dashboardPanel.add(scrollPane);


        frame.setVisible(true);
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
        JPanel dataPanel = new JPanel(){

            // Override paintComponent method to customize appearance
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (title.equals("Total")) {
                    drawDataPanel(g, title, String.format("$%,.2f", totalAmount), getWidth(), getHeight());
                } else {
                    // temporary null arraylist
                    drawDataPanel(g, title, "00", getWidth(), getHeight());

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
