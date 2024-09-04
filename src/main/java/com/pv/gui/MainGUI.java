package com.pv.gui;

import com.pv.controller.FileSelection;
import com.pv.controller.FileSelector;
import com.pv.controller.listener.OpenFileListener;
import com.pv.controller.listener.OpenFolderListener;
import com.pv.model.ImageModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI implements Runnable{

    private JFrame frame;
    private ImagePanel imagePanel;
    private ImageModel model;

    public MainGUI() {
        model = new ImageModel();
    }

    @Override
    public void run() {
        frame = new JFrame();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setJMenuBar(createMenuBar());

        imagePanel = new ImagePanel(model);

        frame.add(imagePanel, BorderLayout.CENTER);

        frame.add(new Panel(),BorderLayout.WEST);
        frame.add(new Panel(),BorderLayout.EAST);
        frame.setVisible(true);
    }

    public JMenuBar createMenuBar() {
        FileSelection file = new OpenFileListener(this,model);
        OpenFolderListener folder = new OpenFolderListener(this,model);

        FileSelector fileSelector = new FileSelector(folder);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Open");
        JMenuItem menuItem = new JMenuItem("File");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileSelector.set(folder);
                fileSelector.select();
            }
        });
        JMenuItem next = new JMenuItem("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    folder.next();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(frame, ex);
                }
            }
        });
        menu.add(menuItem);
        menu.add(next);
        menuBar.add(menu);

        return menuBar;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateImagePanel(int width, int height) {
        imagePanel.setPreferredSize(width, height);
        imagePanel.repaint();
    }
}
