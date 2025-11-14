/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;
import UserInterfaceLayer.MainWindow;

/**
 *
 * @author HP
 */
public class Main {
    
    public static void main(String args[]){
        
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainWindow().setVisible(true);
        
    }
    
}
