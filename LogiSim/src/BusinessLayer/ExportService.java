/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Component;

public class ExportService {

    // Converts a Swing Component (Canvas/JPanel) into an image
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
