package com.pv.gui;

import com.pv.builder.GridBagConstraintsBuilder;
import com.pv.controller.FileSelection;
import com.pv.controller.FileSelector;
import com.pv.controller.listener.OpenFileListener;
import com.pv.controller.listener.OpenFolderListener;
import com.pv.model.ImageModel;

import javax.swing.*;
import java.awt.*;

public class MainGUI implements Runnable{

    private final JFrame frame;
    private final ImagePanel imagePanel;
    private final JPanel filePanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton nextButton;
    private JButton prevButton;

    private final ImageModel model;

    private final FileSelection file;
    private final OpenFolderListener folder;

    private final FileSelector fileSelector;

    public MainGUI() {
        frame = new JFrame();
        model = new ImageModel();
        imagePanel = new ImagePanel(model);
        filePanel = new JPanel();

        file = new OpenFileListener(this,model);
        folder = new OpenFolderListener(this,model);

        fileSelector = new FileSelector();
    }

    @Override
    public void run() {
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        filePanel.setVisible(true);
        filePanel.add(createOpenFile());
        filePanel.add(new JLabel("OR"));
        filePanel.add(createOpenFolder());
        gbc = new GridBagConstraintsBuilder.Builder()
                .setAnchor(GridBagConstraints.CENTER)
                .build();
        frame.add(filePanel,gbc);

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension((int) dimension.getWidth(),50));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Top Panel"));
        topPanel.setVisible(false);
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridWidth(4)
                .setAnchor(GridBagConstraints.NORTH)
                .build();
        frame.add(topPanel, gbc);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension((int) dimension.getWidth(),50));
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(new JLabel("Bottom Panel"));
        bottomPanel.setVisible(false);
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridY(2)
                .setGridWidth(4)
                .setAnchor(GridBagConstraints.SOUTH)
                .build();
        frame.add(bottomPanel, gbc);

        imagePanel.setVisible(false);
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridX(2).setGridY(1)
                .setWeightX(1).setWeightY(1)
                .setAnchor(GridBagConstraints.CENTER)
                .build();
        frame.add(imagePanel, gbc);

        prevButton = createButton("Prev");
        prevButton.addActionListener(e -> folder.prev());
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridX(1).setGridY(1)
                .setGridWidth(1)
                .setAnchor(GridBagConstraints.LINE_START)
                .build();
        frame.add(prevButton, gbc);

        nextButton = createButton("Next");
        nextButton.addActionListener(e -> folder.next());
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridX(3).setGridY(1)
                .setGridWidth(1)
                .setAnchor(GridBagConstraints.LINE_END)
                .build();
        frame.add(nextButton, gbc);

        frame.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setVisible(false);
        return button;
    }

    private JButton createOpenFile() {
        JButton button = new JButton("Open file image");
        button.setPreferredSize(new Dimension(130,20));
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
        button.setPreferredSize(new Dimension(130,20));
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
