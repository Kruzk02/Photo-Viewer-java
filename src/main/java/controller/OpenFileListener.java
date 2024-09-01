package controller;

import gui.MainGUI;
import model.ImageModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenFileListener implements ActionListener {

    private MainGUI mainGUI;
    private ImageModel model;

    public OpenFileListener(MainGUI mainGUI,ImageModel model) {
        this.mainGUI = mainGUI;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            BufferedImage image;

            try {
                image = ImageIO.read(selectedFile);
                model.setImage(image);
                mainGUI.updateImagePanel(image.getWidth(),
                        image.getHeight());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
