package com.grafo.util;

import com.grafo.ui.PanelGrafo;

import javax.swing.*;
import java.awt.*;

/**
 * Clase para gestionar temas visuales de la aplicación
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class GestorTemas {

    private boolean modoOscuro = false;

    private static final Color FONDO_CLARO = Color.WHITE;
    private static final Color PANEL_CLARO = new Color(236, 240, 241);

    private static final Color FONDO_OSCURO = new Color(33, 33, 33);
    private static final Color PANEL_OSCURO = new Color(44, 44, 44);


    public void toggleModoOscuro(JFrame frame, PanelGrafo panelGrafo) {
        modoOscuro = !modoOscuro;

        if (modoOscuro) {
            aplicarModoOscuro(frame, panelGrafo);
        } else {
            aplicarModoClaro(frame, panelGrafo);
        }

        panelGrafo.repaint();
        frame.repaint();
    }

    private void aplicarModoOscuro(JFrame frame, PanelGrafo panelGrafo) {
        frame.getContentPane().setBackground(FONDO_OSCURO);
        panelGrafo.setBackground(PANEL_OSCURO);
    }

    private void aplicarModoClaro(JFrame frame, PanelGrafo panelGrafo) {
        frame.getContentPane().setBackground(FONDO_CLARO);
        panelGrafo.setBackground(PANEL_CLARO);
    }

    public boolean isModoOscuro() {
        return modoOscuro;
    }

    public String getMensajeModoActual() {
        return modoOscuro ? "Modo oscuro activado" : "Modo claro activado";
    }
}
