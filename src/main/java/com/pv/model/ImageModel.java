package com.pv.model;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageModel {

    private BufferedImage image;
    private File file;

    public ImageModel() {
    }

    public ImageModel(BufferedImage image, File file) {
        this.image = image;
        this.file = file;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
