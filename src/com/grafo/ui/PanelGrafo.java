package com.grafo.ui;

import com.grafo.model.*;
import com.grafo.util.RenderizadorGrafo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelGrafo extends JPanel {

    private final Grafo grafo;
    private Integer nodoSeleccionado1;
    private List<Integer> caminoResaltado;
    private Color colorCamino;
    private double escalaZoom = 1.0;

    private static final int ANCHO_TOTAL = 3000;
    private static final int ALTO_TOTAL = 3000;

    public PanelGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.nodoSeleccionado1 = null;
        this.caminoResaltado = null;
        this.colorCamino = RenderizadorGrafo.getColorCamino();

        setPreferredSize(new Dimension(ANCHO_TOTAL, ALTO_TOTAL));
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 5),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(escalaZoom, escalaZoom);

        RenderizadorGrafo.dibujarGrafo(g2d, grafo);

        if (caminoResaltado != null && !caminoResaltado.isEmpty()) {
            RenderizadorGrafo.dibujarCamino(g2d, grafo, caminoResaltado, colorCamino);
        }

        if (nodoSeleccionado1 != null) {
            Nodo nodo = grafo.getNodo(nodoSeleccionado1);
            if (nodo != null) {
                RenderizadorGrafo.dibujarNodoSeleccionado(g2d, nodo);
            }
        }
    }

    public void setEscalaZoom(double escala) {
        this.escalaZoom = escala;
    }

    public double getEscalaZoom() {
        return this.escalaZoom;
    }

    public void setNodoSeleccionado1(Integer nodo) {
        this.nodoSeleccionado1 = nodo;
    }

    public void setCaminoResaltado(List<Integer> camino) {
        this.caminoResaltado = camino;
    }

    public void setColorCamino(Color color) {
        this.colorCamino = color;
    }

    public void limpiarResaltado() {
        this.caminoResaltado = null;
        this.nodoSeleccionado1 = null;
    }
}
