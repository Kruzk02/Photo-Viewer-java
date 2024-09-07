package com.pv.gui;

import com.pv.controller.FileSelection;
import com.pv.controller.FileSelector;
import com.pv.controller.listener.OpenFileListener;
import com.pv.controller.listener.OpenFolderListener;
import com.pv.model.ImageModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainGUI implements Runnable{

    private JFrame frame;
    private final ImagePanel imagePanel;
    private final JPanel filePanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
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
        imagePanel.setBorder(new LineBorder(Color.GREEN));
        filePanel = new JPanel();

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

        filePanel.setVisible(true);
        filePanel.add(createOpenFile());
        filePanel.add(new JLabel("OR"));
        filePanel.add(createOpenFolder());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(filePanel,gbc);

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension((int) dimension.getWidth(),50));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Top Panel"));
        topPanel.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(topPanel, gbc);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension((int) dimension.getWidth(),50));
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(new JLabel("Bottom Panel"));
        bottomPanel.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        frame.add(bottomPanel, gbc);

        imagePanel.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 0;
        gbc.gridheight = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(imagePanel, gbc);

        prevButton = new JButton("Prev");
        prevButton.setVisible(false);
        prevButton.addActionListener(e -> folder.prev());

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(prevButton, gbc);

        nextButton = new JButton("Next");
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> folder.next());

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(nextButton, gbc);

        frame.setVisible(true);
    }

    private JButton createOpenFile() {
        JButton button = new JButton("Open file image");
        button.addActionListener(e -> {
            fileSelector.set(file);
            fileSelector.select();

            if (model.getImage() != null) {
                filePanel.setVisible(false);
                topPanel.setVisible(true);
                bottomPanel.setVisible(true);
                imagePanel.setVisible(true);
            }else {
                filePanel.setVisible(true);
                topPanel.setVisible(false);
                bottomPanel.setVisible(false);
                imagePanel.setVisible(false);
            }
        });
        return button;
    }

    private JButton createOpenFolder() {
        JButton button = new JButton("Open folder image");
        button.addActionListener(e -> {
            fileSelector.set(folder);
            fileSelector.select();

            if (model.getImage() != null) {
                filePanel.setVisible(false);
                topPanel.setVisible(true);
                bottomPanel.setVisible(true);
                imagePanel.setVisible(true);
                prevButton.setVisible(true);
                nextButton.setVisible(true);
            }else {
                filePanel.setVisible(true);
                topPanel.setVisible(false);
                bottomPanel.setVisible(false);
                imagePanel.setVisible(false);
                prevButton.setVisible(false);
                nextButton.setVisible(false);
            }
        });
        return button;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateImagePanel(int width, int height) {
        imagePanel.setPreferredSize(width - 30, height - 30);
        imagePanel.repaint();
    }
}
