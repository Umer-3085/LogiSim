/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import UserInterfaceLayer.MainWindow;
import javax.swing.UIManager;

/**
 * Entry point of the Logisim application.
 * This class initializes the UI look and feel
 * and launches the main application window.
 *
 * @author HP
 */
public class Main {
    
    /**
     * The main method serves as the starting point of the application.
     * 
     * @param args command-line arguments (not used) 
     */
    public static void main(String args[]){
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainWindow().setVisible(true);
        
    }
    
}
