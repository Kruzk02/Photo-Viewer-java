package com.pv.compoment;

import com.pv.model.ImageModel;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private final ImageModel model;
    private double zoomFactor = 1.0;

    public ImagePanel(ImageModel model) {
        this.model = model;
    }

    public void zoomIn() {
        if (zoomFactor < 5.0) {
            zoomFactor *= 1.2;
            updatePreferredSize();
            repaint();
        }
    }

    public void zoomOut() {
        if (zoomFactor > 0.3) {
            zoomFactor /= 1.2;
            updatePreferredSize();
            repaint();
        }
    }

    private void updatePreferredSize() {
        if (model.getImage() != null) {
            int imgWidth = (int) (model.getImage().getWidth() * zoomFactor);
            int imgHeight = (int) (model.getImage().getHeight() * zoomFactor);
            super.setPreferredSize(new Dimension(imgWidth, imgHeight));
            revalidate();
        }
    }
    public void setPreferredSize(int width, int height) {
        this.setPreferredSize(new Dimension(width,height));
        revalidate();
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

            int zoomedWidth = (int) (imgWidth * zoomFactor);
            int zoomedHeight = (int) (imgHeight * zoomFactor);

            int x = (panelWidth - zoomedWidth) / 2;
            int y = (panelHeight - zoomedHeight) / 2;

            g2d.drawImage(model.getImage(), x, y, zoomedWidth, zoomedHeight, this);
        }
    }
}
