/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Palette panel for the circuit design application.
 * <p>
 * Contains buttons for logic gates, input/output components,
 * and wires. Provides an interface for users to select
 * components/tools to place on the canvas.
 * </p>
 * 
 * Features:
 * <ul>
 *     <li>Logic gates: AND, OR, NOT, XOR, NAND, NOR</li>
 *     <li>Input/Output components: Switch, LED</li>
 *     <li>Wire tool</li>
 *     <li>Section labels for grouping components</li>
 *     <li>Ability to register a single ActionListener for all buttons</li>
 * </ul>
 * 
 * Example usage:
 * <pre>
 *     Palette palette = new Palette();
 *     palette.addToolSelectionListener(e -> {
 *         String tool = e.getActionCommand();
 *         canvas.setActiveTool(tool);
 *     });
 * </pre>
 * 
 * @author HP
 */
public class Palette extends JPanel {
    
    /** Buttons for logic gates */
    private static JButton andBtn;
    private static JButton orBtn;
    private static JButton notBtn;
    private static JButton xorBtn;
    private static JButton nandBtn;
    private static JButton norBtn;
    /** Buttons for input/output and wire */
    private static JButton ledBtn;
    private static JButton wireBtn;
    private static JButton switchBtn;
    
    
    /**
     * Constructs the Palette panel.
     * Adds sections for logic gates and input/output components
     * along with properly styled buttons.
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
     * Creates a JLabel for a section header in the palette.
     * 
     * @param text the section title
     * @return a styled JLabel
     */
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    /**
     * Creates a styled JButton for a component/tool.
     * 
     * @param text the button text
     * @return a JButton configured for the palette
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
     * Adds a single ActionListener to all buttons in the palette.
     * Useful for setting the active tool/component for the canvas.
     * 
     * @param listener the ActionListener to attach to all buttons
     */
    public void addToolSelectionListener(ActionListener listener) {
        for (Component c : this.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(listener);
            }
        }
    }

    
}