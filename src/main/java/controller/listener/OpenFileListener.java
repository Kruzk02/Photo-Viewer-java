package controller.listener;

import controller.FileSelection;
import gui.MainGUI;
import model.ImageModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenFileListener implements FileSelection {

    private final MainGUI mainGUI;
    private final ImageModel model;

    public OpenFileListener(MainGUI mainGUI,ImageModel model) {
        this.mainGUI = mainGUI;
        this.model = model;
    }

    @Override
    public void select() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        chooser.addChoosableFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        File file = model.getFile();
        if (file != null) {
            chooser.setCurrentDirectory(file);
        }

        int result = chooser.showOpenDialog(mainGUI.getFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            model.setFile(selectedFile);
            display(selectedFile);
        }
    }

    private void display(File currentFile) {
        try {
            BufferedImage image = ImageIO.read(currentFile);
            model.setImage(image);
            mainGUI.updateImagePanel(image.getWidth(),image.getHeight());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
