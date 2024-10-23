package com.pv.folder;

import javax.swing.*;
import java.io.File;

public class FolderSelector {

    public File[] select() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            if (selectedFolder != null && selectedFolder.isDirectory()) {
                File[] fileList = selectedFolder.listFiles((dir, name) ->
                        name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
                if (fileList != null && fileList.length > 0) {
                    return fileList;
                } else {
                    System.out.println("No image files found in the selected folder.");
                }
            } else {
                System.out.println("Selected file is not a directory.");
            }
        } else {
            System.out.println("Folder selection was cancelled.");
        }
        return new File[0];
    }
}
