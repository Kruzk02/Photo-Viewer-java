package gui;

import controller.OpenFileListener;
import model.ImageModel;

import javax.swing.*;
import java.awt.*;

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
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Open");
        JMenuItem menuItem = new JMenuItem("File");
        menuItem.addActionListener(new OpenFileListener(this,model));
        menu.add(menuItem);
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
