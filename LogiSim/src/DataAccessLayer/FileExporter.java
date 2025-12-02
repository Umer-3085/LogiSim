package DataAccessLayer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.image.BufferedImage;

public class FileExporter {

    public void saveImage(BufferedImage img, String defaultName) throws Exception {

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(defaultName + ".png"));

        int option = chooser.showSaveDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Always save as PNG for now
            ImageIO.write(img, "png", file);
        }
    }
}