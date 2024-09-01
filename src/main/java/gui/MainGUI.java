package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainGUI implements Runnable{

    private JFrame frame;

    public MainGUI() {
    }

    @Override
    public void run() {
        frame = new JFrame("Image");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImagePanel imagePanel = new ImagePanel();

        frame.add(imagePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public JFrame frame() {
        return frame;
    }
}
