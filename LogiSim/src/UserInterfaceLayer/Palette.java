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
import javax.swing.border.*;
import java.awt.*;

public class Palette extends JPanel {
    
    /**
     * 
     */
    public Palette() {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        TitledBorder titleBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.lightGray, 2), 
            "Palette", 
            TitledBorder.CENTER, 
            TitledBorder.TOP
        );
       
        titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titleBorder.setTitleColor(Color.DARK_GRAY);
        
        setBorder(titleBorder);
        setBackground(Color.WHITE); 
        
        add(createSectionLabel("Logic Gates"));
        add(createComponentButton("AND Gate"));
        add(createComponentButton("OR Gate")); 
        add(createComponentButton("NOT Gate"));
        add(createComponentButton("XOR Gate"));
        add(createComponentButton("NAND Gate"));
        add(createComponentButton("NOR Gate"));
        
        add(Box.createVerticalStrut(10));
        
        add(createSectionLabel("Input/Output"));
        add(createComponentButton("Switch"));
        add(createComponentButton("Button"));
        add(createComponentButton("LED"));
        
        add(Box.createVerticalStrut(10));
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    private JButton createComponentButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(140, 30));
        button.setFocusPainted(true);
        button.setBackground(Color.WHITE);
        button.setOpaque(true); 
        button.setMargin(new Insets(5, 10, 5, 10)); 
        return button;
    }
}