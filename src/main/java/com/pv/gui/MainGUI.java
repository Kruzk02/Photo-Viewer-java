package com.pv.gui;

import com.pv.controller.FileSelection;
import com.pv.controller.FileSelector;
import com.pv.controller.listener.OpenFileListener;
import com.pv.controller.listener.OpenFolderListener;
import com.pv.model.ImageModel;

import javax.swing.*;
import java.awt.*;

public class MainGUI implements Runnable{

    private JFrame frame;
    private final ImagePanel imagePanel;
    private JButton nextButton;
    private JButton prevButton;

    private final ImageModel model;

    FileSelection file;
    OpenFolderListener folder;

    FileSelector fileSelector;
    FileSelector folderSelector;

    public MainGUI() {
        model = new ImageModel();
        imagePanel = new ImagePanel(model);

        file = new OpenFileListener(this,model);
        folder = new OpenFolderListener(this,model);

        fileSelector = new FileSelector(file);
        folderSelector = new FileSelector(folder);
    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setJMenuBar(createMenuBar());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension((int) dimension.getWidth(),50));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Top Panel"));
        topPanel.setVisible(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(topPanel, gbc);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension((int) dimension.getWidth(),50));
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(new JLabel("Bottom Panel"));
        bottomPanel.setVisible(true);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        frame.add(bottomPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(imagePanel, gbc);

        prevButton = new JButton("Prev");
        prevButton.setVisible(true);
        prevButton.addActionListener(e -> folder.prev());

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(prevButton, gbc);

        nextButton = new JButton("Next");
        nextButton.setVisible(true);
        nextButton.addActionListener(e -> folder.next());

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(nextButton, gbc);


        frame.revalidate();
        frame.repaint();

        frame.setVisible(true);
    }



    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Open");
        JMenuItem fileMenuItem = getFileMenuItem();

        JMenuItem folderMenuItem = new JMenuItem("Folder");
        folderMenuItem.addActionListener(e -> {
            folderSelector.set(folder);
            folderSelector.select();
            if (model.getImage() != null) {
                prevButton.setVisible(true);
                imagePanel.setVisible(true);
                nextButton.setVisible(true);
            }else {
                imagePanel.setVisible(false);
                prevButton.setVisible(false);
                nextButton.setVisible(false);
            }
        });
        menu.add(fileMenuItem);
        menu.add(folderMenuItem);
        menuBar.add(menu);

        return menuBar;
    }

    private JMenuItem getFileMenuItem() {
        JMenuItem fileMenuItem = new JMenuItem("File");
        fileMenuItem.addActionListener(e -> {
            fileSelector.set(file);
            fileSelector.select();
            imagePanel.setVisible(model.getFile() != null);
            prevButton.setVisible(false);
            nextButton.setVisible(false);
        });
        return fileMenuItem;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateImagePanel(int width, int height) {
        imagePanel.setPreferredSize(width, height);
        imagePanel.repaint();
    }
}
