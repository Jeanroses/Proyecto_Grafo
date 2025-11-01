package com.grafo.util;

/**
 * @author Jean Paul Purihuaman De La Cruz
 */
public class GestorZoom {

    private double zoomFactor = 1.0;

    private static final double ZOOM_MIN = 0.5;
    private static final double ZOOM_MAX = 3.0;
    private static final double ZOOM_STEP = 0.1;

    public boolean zoomIn() {
        if (zoomFactor < ZOOM_MAX) {
            zoomFactor += ZOOM_STEP;
            return true;
        }
        return false;
    }

    public boolean zoomOut() {
        if (zoomFactor > ZOOM_MIN) {
            zoomFactor -= ZOOM_STEP;
            return true;
        }
        return false;
    }

    public void zoomReset() {
        zoomFactor = 1.0;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public int getZoomPercentage() {
        return (int) (zoomFactor * 100);
    }

    public String getMensajeZoom() {
        return "Zoom: " + getZoomPercentage() + "%";
    }
}
