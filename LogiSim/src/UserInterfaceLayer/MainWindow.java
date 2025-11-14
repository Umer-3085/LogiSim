/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 *
 * @author HP
 */
public class MainWindow extends JFrame {
    
    public MainWindow(){
        
        setInitialGUIComponent(this.getContentPane());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 1000, 600);
        
    }
    
    public static void setInitialGUIComponent(Container pane){
        
        JPanel panel1 = setFirstPanel();
        
        pane.add(panel1,BorderLayout.PAGE_START);
        
    } 
    
    public static JPanel setFirstPanel(){
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel softwareName = new JLabel("LOGISIM");
        softwareName.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel1.add(softwareName);
        return panel1;
        
    }
    
}
