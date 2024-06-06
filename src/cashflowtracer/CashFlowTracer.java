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
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

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
        tableModel = new DefaultTableModel(columneNames, 0);
        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);


        frame.setVisible(true);
    }
    
    
}
