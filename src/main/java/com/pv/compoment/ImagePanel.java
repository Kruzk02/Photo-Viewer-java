package com.pv.compoment;

import com.pv.model.ImageModel;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private final ImageModel model;
    private final Zoom zoom;

    public ImagePanel(ImageModel model) {
        this.model = model;
        this.zoom = new Zoom();
    }

    public void zoomIn() {
        zoom.setZoomFactor(zoom.zoomIn());
        updatePreferredSize();
        repaint();
    }

    public void zoomOut() {
        zoom.setZoomFactor(zoom.zoomOut());
        updatePreferredSize();
        repaint();
    }

    private void updatePreferredSize() {
        setPreferredSize(new Dimension(model.getImage().getWidth(), model.getImage().getHeight()));
        revalidate();
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

            int zoomedWidth = (int) (imgWidth * zoom.getZoomFactor());
            int zoomedHeight = (int) (imgHeight * zoom.getZoomFactor());

            int x = (panelWidth - zoomedWidth) / 2;
            int y = (panelHeight - zoomedHeight) / 2;

            g2d.drawImage(model.getImage(), x, y, zoomedWidth, zoomedHeight, this);
        }
    }
}
