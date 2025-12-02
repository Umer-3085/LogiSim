/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Component;

/**
 * Service class responsible for exporting Swing components as images.
 * <p>
 * This class provides functionality to convert any {@link Component} 
 * (such as {@link javax.swing.JPanel} or {@link java.awt.Canvas}) 
 * into a {@link BufferedImage}.
 * </p>
 * @author HP
 */

public class ExportService {

     /**
     * Renders the given Swing component into a {@link BufferedImage}.
     * <p>
     * This method creates an image with the same width and height as the 
     * component and paints the component onto it.
     * </p>
     *
     * @param comp the {@link Component} to be rendered into an image
     * @return a {@link BufferedImage} representing the visual content of the component
     */
    public BufferedImage renderComponent(Component comp) {
        BufferedImage img = new BufferedImage(
                comp.getWidth(),
                comp.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = img.createGraphics();
        comp.paint(g2d);
        g2d.dispose();

        return img;
    }
}
