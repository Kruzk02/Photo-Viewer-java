package com.pv.gui;

import com.pv.builder.GridBagConstraintsBuilder;
import com.pv.compoment.Button;
import com.pv.compoment.ImagePanel;
import com.pv.folder.FolderIterator;
import com.pv.folder.FolderIteratorImpl;
import com.pv.folder.FolderSelector;
import com.pv.model.ImageModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGUI implements Runnable{

    private final JFrame frame;
    private final ImagePanel imagePanel;
    private final JPanel filePanel;
    private JPanel centerPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton nextButton;
    private JButton prevButton;
    private CardLayout cl;
    private GridBagConstraints gbc;

    private final ImageModel model;

    private final FolderSelector folderSelector;
    private FolderIterator folderIterator;
    public MainGUI() {
        frame = new JFrame();
        model = new ImageModel();
        imagePanel = new ImagePanel(model);
        filePanel = new JPanel();

        folderSelector = new FolderSelector();
    }

    @Override
    public void run() {
        setupFrame();
        cl = (CardLayout) (centerPanel.getLayout());
        cl.show(centerPanel, "FilePanel");

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateImagePanel(int width, int height) {
        imagePanel.setPreferredSize(width, height);
        imagePanel.repaint();
    }

    private void setupFrame() {
        frame.setLayout(new GridBagLayout());
        frame.setBackground(Color.LIGHT_GRAY);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        addPanelsToFrame(dimension);
    }

    private void addPanelsToFrame(Dimension dimension) {
        frame.add(createCenterPanel(), gbc);
        frame.add(createTopPanel(dimension), gbc);
        frame.add(createBottomPanel(dimension), gbc);
        frame.add(createWestPanel(), gbc);
        frame.add(createEastPanel(), gbc);
    }

    private JPanel createCenterPanel() {
        centerPanel = new JPanel(new CardLayout());
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridX(1).setGridY(1)
                .setGridWidth(1).setGridHeight(1)
                .setWeightX(1.0).setWeightY(1.0)
                .setFill(GridBagConstraints.BOTH)
                .build();
        filePanel.setVisible(true);
        filePanel.add(createOpenFolder());
        centerPanel.add(filePanel, "FilePanel");

        imagePanel.setVisible(false);
        centerPanel.add(imagePanel, "ImagePanel");
        return centerPanel;
    }

    private JPanel createTopPanel(Dimension dimension) {
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension((int) dimension.getWidth(), 35));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Top Panel"));
        topPanel.setVisible(false);
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridWidth(3).setGridHeight(1)
                .setFill(GridBagConstraints.HORIZONTAL)
                .build();
        return topPanel;
    }

    private JPanel createBottomPanel(Dimension dimension) {
        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setPreferredSize(new Dimension((int) dimension.getWidth(), 35));
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(new JLabel("Bottom Panel"));
        bottomPanel.setVisible(false);
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridY(2)
                .setGridWidth(3).setGridHeight(1)
                .setWeightX(1.0)
                .setFill(GridBagConstraints.HORIZONTAL)
                .build();
        return bottomPanel;
    }

    private JPanel createWestPanel() {
        JPanel westPanel = new JPanel(new GridBagLayout());
        prevButton = new Button("Prev");
        prevButton.addActionListener(e -> {
            if (folderIterator.hasPrev()) display(folderIterator.prev());
        });
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridY(1)
                .setGridWidth(1).setGridHeight(1)
                .setWeightY(1.0)
                .setFill(GridBagConstraints.NONE)
                .setAnchor(GridBagConstraints.CENTER)
                .build();
        westPanel.add(prevButton, gbc);
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridY(1)
                .setGridWidth(1).setGridHeight(2)
                .setWeightY(1.0)
                .setFill(GridBagConstraints.BOTH)
                .build();
        return westPanel;
    }

    private JPanel createEastPanel() {
        JPanel eastPanel = new JPanel(new GridBagLayout());
        nextButton = new Button("Next");
        nextButton.addActionListener(e -> {
            if (folderIterator.hasNext()) display(folderIterator.next());
        });
        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridX(2).setGridY(1)
                .setGridWidth(1).setGridHeight(1)
                .setWeightY(1.0)
                .setFill(GridBagConstraints.NONE)
                .setAnchor(GridBagConstraints.CENTER)
                .build();
        eastPanel.add(nextButton, gbc);

        gbc = new GridBagConstraintsBuilder.Builder()
                .setGridX(2).setGridY(1)
                .setGridWidth(1).setGridHeight(2)
                .setWeightY(1.0)
                .setFill(GridBagConstraints.BOTH)
                .build();
        return eastPanel;
    }

    private JButton createOpenFolder() {
        JButton button = new Button("Open folder image");
        button.setVisible(true);
        button.addActionListener(e -> {
            File[] files = folderSelector.select();
            folderIterator = new FolderIteratorImpl(files);
            display(files[0]);

            if (model.getImage() != null) {
                cl.show(centerPanel,"ImagePanel");
                topPanel.setVisible(true);
                bottomPanel.setVisible(true);
                prevButton.setVisible(true);
                nextButton.setVisible(true);
            }else {
                cl.show(centerPanel,"FilePanel");
                topPanel.setVisible(false);
                bottomPanel.setVisible(false);
                prevButton.setVisible(false);
                nextButton.setVisible(false);
            }
        });
        return button;
    }

    private void display(File currentFile) {
        try {
            BufferedImage image = ImageIO.read(currentFile);
            model.setImage(image);
            this.updateImagePanel(image.getWidth(),image.getHeight());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
