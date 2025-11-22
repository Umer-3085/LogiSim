/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

/**
 *
 * @author HP
 */
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CircuitExplorer extends JPanel {
    
    public JList<String> circuitList;
    public DefaultListModel<String> circuitModel;
    private static JButton addCircuitBtn;
    private static JButton removeCircuitBtn;
    
    /**
     * 
     */
    public CircuitExplorer() {
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 400));
        setBackground(Color.WHITE);
        
        TitledBorder titleBorder = BorderFactory.createTitledBorder("Circuit Explorer");
        
        titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titleBorder.setTitleColor(Color.darkGray);
        setBorder(titleBorder);
        
        circuitModel = new DefaultListModel<>();
        circuitList = new JList<>(circuitModel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.white);
        addCircuitBtn = new JButton("New Circuit");
        removeCircuitBtn = new JButton("Remove");
        
        buttonPanel.add(addCircuitBtn);
        buttonPanel.add(removeCircuitBtn);
        
        add(new JScrollPane(circuitList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}