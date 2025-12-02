package DataAccessLayer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.image.BufferedImage;

/**
 * Utility class to export images to the filesystem.
 * <p>
 * Provides functionality to save a {@link BufferedImage} to a user-specified location
 * using a {@link JFileChooser} dialog. Currently, images are always saved in PNG format.
 * </p>
 * 
 * <p>Example usage:</p>
 * <pre>
 *     FileExporter exporter = new FileExporter();
 *     BufferedImage image = ...; // generate or obtain your image
 *     exporter.saveImage(image, "myCircuit");
 * </pre>
 * 
 * @author HP
 */
public class FileExporter {

    /**
     * Opens a file chooser dialog to save the given image to disk.
     * The default filename is suggested in the dialog, and the image
     * is always saved as a PNG file.
     *
     * @param img The {@link BufferedImage} to save
     * @param defaultName The default filename (without extension) suggested in the save dialog
     * @throws Exception if an error occurs during file saving
     */
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