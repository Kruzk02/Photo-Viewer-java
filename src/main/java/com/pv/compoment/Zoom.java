package com.pv.compoment;

public class Zoom {

    private double zoomFactor = 1.0;

    public double zoomIn() {
        if (zoomFactor < 5.0) {
            return zoomFactor *= 1.2;
        }
        return zoomFactor;
    }

    public double zoomOut() {
        if (zoomFactor > 0.3) {
            return zoomFactor /= 1.2;
        }
        return zoomFactor;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
}
