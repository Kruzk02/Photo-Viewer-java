package gui;

import model.ImageModel;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private final ImageModel model;

    public ImagePanel(ImageModel model) {
        this.model = model;
    }

    public void setPreferredSize(int width, int height) {
        this.setPreferredSize(new Dimension(width,height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model.getImage() != null) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = model.getImage().getWidth();
            int imgHeight = model.getImage().getHeight();

            double aspectRatio = (double) imgWidth / imgHeight;

            int newWidth = panelWidth;
            int newHeight = (int) (newWidth / aspectRatio);

            if (newHeight > panelHeight) {
                newHeight = panelHeight;
                newWidth = (int) (newHeight * aspectRatio);
            }

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;
            g2d.drawImage(model.getImage(), x, y, newWidth, newHeight, this);
        }
    }

}
