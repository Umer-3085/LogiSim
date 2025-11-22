/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

/**
 *
 * @author HP
 */
public class MainWindow extends JFrame {
    
    private static JButton newButton;
    private static JButton saveButton;
    private static JButton exportButton;
    private static JButton loadButton;
    private static JToolBar toolBar;
    
    /**
     * 
     */
    public MainWindow(){
        
        setInitialGUIComponent(this.getContentPane());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 1000, 600);
        setEventHandlers();
        
    }
    
    /**
     * 
     * @param pane 
     */
    public static void setInitialGUIComponent(Container pane){
        
        JPanel panel1 = setHeaderFirstPanel();
        JPanel panel2 = setHeaderSecondPanel();
        
        JPanel BPanel1 = new JPanel();
        BPanel1.setLayout(new BoxLayout(BPanel1,BoxLayout.Y_AXIS));
        BPanel1.add(panel1);
        BPanel1.add(panel2);
                
        pane.add(BPanel1,BorderLayout.PAGE_START);
        pane.add(new Palette(),BorderLayout.LINE_START);
        pane.add(createCenterCanvas(),BorderLayout.CENTER);
        pane.add(new CircuitExplorer(), BorderLayout.LINE_END);
        
    } 
    
    /**
     * 
     * @return 
     */
    public static JPanel setHeaderFirstPanel(){
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout()); 
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        JLabel projectName = new JLabel("Project: Untitled");
        projectName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        projectName.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        JLabel softwareName = new JLabel("LOGISIM",SwingConstants.CENTER);
        softwareName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel currentCircuit = new JLabel("Circuit: Main");
        currentCircuit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        currentCircuit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panel1.add(projectName, BorderLayout.WEST);
        panel1.add(softwareName, BorderLayout.CENTER);
        panel1.add(currentCircuit, BorderLayout.EAST);
        panel1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        return panel1;
        
    }
    
    /**
     * 
     * @return 
     */
    public static JPanel setHeaderSecondPanel(){
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar = new JToolBar();
        toolBar.setFloatable(false); 
        
        newButton = new JButton("   New Project   ");
        newButton.setToolTipText("Create a new Project");
        saveButton = new JButton("   Save   ");
        saveButton.setToolTipText("Save this Project");
        loadButton = new JButton("   Load   ");
        loadButton.setToolTipText("Load a existing Project");
        exportButton = new JButton("   Export as PNG/JPG   ");
        exportButton.setToolTipText("Export this project as a PNG/JPG");
        
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(newButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(saveButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(loadButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(exportButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.setBackground(Color.WHITE);
        
        panel2.add(toolBar);
        panel2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        panel2.setBackground(Color.WHITE); 
        return panel2;
        
    }
    
    /**
     * 
     * @return 
     */
    public static JComponent createCenterCanvas() {
        JPanel canvas = new JPanel();
        canvas.setBackground(Color.WHITE);
        TitledBorder titleBorder = BorderFactory.createTitledBorder("Circuit Design Area");    
        titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titleBorder.setTitleColor(Color.darkGray);
        canvas.setBorder(titleBorder);
        return canvas;
    }
    
    /**
     * 
     */
    public static void setEventHandlers(){
        
        newButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                
            }
        });
        
        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                
            }
        });
        
        exportButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                
            }
        });
        
        loadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                
            }
        });
        
    }
    
}
