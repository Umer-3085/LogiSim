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
import java.awt.event.*;

public class Palette extends JPanel {
    
    private static JButton andBtn;
    private static JButton orBtn;
    private static JButton notBtn;
    private static JButton xorBtn;
    private static JButton nandBtn;
    private static JButton norBtn;
    private static JButton ledBtn;
    private static JButton wireBtn;
    private static JButton switchBtn;
    
    
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
        andBtn = createComponentButton("AND");
        add(andBtn);
        orBtn = createComponentButton("OR"); 
        add(orBtn);
        notBtn = createComponentButton("NOT");
        add(notBtn);
        xorBtn = createComponentButton("XOR");
        add(xorBtn);
        nandBtn = createComponentButton("NAND");
        add(nandBtn);
        norBtn = createComponentButton("NOR");
        add(norBtn);
        
        add(Box.createVerticalStrut(10));
        
        add(createSectionLabel("Input/Output"));
        switchBtn = createComponentButton("Switch");
        add(switchBtn);
        wireBtn = createComponentButton("Wire");
        add(wireBtn);
        ledBtn = createComponentButton("LED");
        add(ledBtn);
        
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
    
    /**
     * 
     */
    public void addToolSelectionListener(ActionListener listener) {
        for (Component c : this.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(listener);
            }
        }
    }

    
}