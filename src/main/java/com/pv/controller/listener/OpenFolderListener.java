package com.pv.controller.listener;

import com.pv.controller.FileSelection;
import com.pv.gui.MainGUI;
import com.pv.model.ImageModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OpenFolderListener implements FileSelection {

    private final MainGUI mainGUI;
    private final ImageModel model;
    private final Queue<File> files;
    private File currentFile;

    public OpenFolderListener(MainGUI mainGUI, ImageModel model) {
        this.mainGUI = mainGUI;
        this.model = model;
        this.files = new LinkedList<>();
        this.currentFile = null;
    }

    @Override
    public void select() {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        File file = model.getFile();
        if (file != null) {
            chooser.setCurrentDirectory(file);
        }

        int result = chooser.showOpenDialog(mainGUI.getFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            model.setFile(selectedFolder);
            files.clear();
            loadFilesFromFolder(selectedFolder);

            if (!files.isEmpty()) {
                currentFile = files.peek();
                display(currentFile);
            }
        }
    }

    public void next() {
        if (files.isEmpty() || currentFile == null) throw new NullPointerException();

        List<File> fileList = new LinkedList<>(files);
        int currentIndex = fileList.indexOf(currentFile);

        if (currentIndex < fileList.size() - 1) {
            currentFile = fileList.get(currentIndex + 1);
            display(currentFile);
        }
    }

    public void prev() {
        if (files.isEmpty() || currentFile == null) throw new NullPointerException();

        List<File> fileList = new LinkedList<>(files);
        int currentIndex = fileList.indexOf(currentFile);

        if (currentIndex > 0) {
            currentFile = fileList.get(currentIndex - 1);
            display(currentFile);
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

    private void loadFilesFromFolder(File folder) {
        if (folder.isDirectory()) {
            File[] fileList = folder.listFiles((dir,name) ->
                    name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
            if (fileList != null) {
                Collections.addAll(files, fileList);
            }
        }
    }
}
