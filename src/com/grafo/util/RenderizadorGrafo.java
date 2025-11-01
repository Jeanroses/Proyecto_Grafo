package com.grafo.util;

import com.grafo.model.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class RenderizadorGrafo {

    private static final int RADIO_NODO = 20;
    private static final int DIAMETRO_NODO = RADIO_NODO * 2;
    private static final Color COLOR_NODO = new Color(41, 128, 185);
    private static final Color COLOR_NODO_BORDE = new Color(25, 75, 135);
    private static final Color COLOR_ARISTA = new Color(52, 73, 94);
    private static final Color COLOR_CAMINO = new Color(46, 204, 113);
    private static final Color COLOR_SELECCIONADO = new Color(230, 126, 34);
    private static final Color COLOR_TEXTO = Color.WHITE;

    public static void dibujarGrafo(Graphics2D g2d, Grafo grafo) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        dibujarAristas(g2d, grafo);
        dibujarNodos(g2d, grafo);
    }

    private static void dibujarAristas(Graphics2D g2d, Grafo grafo) {
        int[][] matriz = grafo.getMatrizAdyacencia();
        int[][] pesos = grafo.getMatrizPesos();
        List<Nodo> nodos = grafo.getNodos();

        for (int i = 0; i < nodos.size(); i++) {
            for (int j = i + 1; j < nodos.size(); j++) {
                if (matriz[i][j] == 1) {
                    Nodo nodo1 = nodos.get(i);
                    Nodo nodo2 = nodos.get(j);
                    int peso = pesos[i][j];

                    dibujarArista(g2d, nodo1, nodo2, peso, COLOR_ARISTA);
                }
            }
        }
    }

    public static void dibujarArista(Graphics2D g2d, Nodo nodo1, Nodo nodo2, int peso, Color color) {
        int x1 = nodo1.getX() + RADIO_NODO;
        int y1 = nodo1.getY() + RADIO_NODO;
        int x2 = nodo2.getX() + RADIO_NODO;
        int y2 = nodo2.getY() + RADIO_NODO;

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));

        if (peso > 0) {
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;

            String pesoStr = String.valueOf(peso);
            g2d.setFont(new Font("Arial", Font.BOLD, 13));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(pesoStr);
            int textHeight = fm.getHeight();
            int padding = 4;

            int bgX = midX - textWidth/2 - padding;
            int bgY = midY - textHeight/2 - padding/2;
            int bgWidth = textWidth + padding * 2;
            int bgHeight = textHeight + padding/2;

            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillRoundRect(bgX + 2, bgY + 2, bgWidth, bgHeight, 6, 6);

            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(bgX, bgY, bgWidth, bgHeight, 6, 6);

            g2d.setColor(new Color(189, 195, 199));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(bgX, bgY, bgWidth, bgHeight, 6, 6);

            g2d.setColor(new Color(192, 57, 43));
            g2d.setFont(new Font("Arial", Font.BOLD, 13));
            g2d.drawString(pesoStr, midX - textWidth/2, midY + fm.getAscent()/2);
        }
    }

    private static void dibujarNodos(Graphics2D g2d, Grafo grafo) {
        List<Nodo> nodos = grafo.getNodos();

        for (Nodo nodo : nodos) {
            dibujarNodo(g2d, nodo, COLOR_NODO);
        }
    }

    public static void dibujarNodo(Graphics2D g2d, Nodo nodo, Color color) {
        int x = nodo.getX();
        int y = nodo.getY();

        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillOval(x + 3, y + 3, DIAMETRO_NODO, DIAMETRO_NODO);

        g2d.setColor(color);
        g2d.fillOval(x, y, DIAMETRO_NODO, DIAMETRO_NODO);

        g2d.setColor(COLOR_NODO_BORDE);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawOval(x, y, DIAMETRO_NODO, DIAMETRO_NODO);

        String idStr = String.valueOf(nodo.getId());
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (DIAMETRO_NODO - fm.stringWidth(idStr)) / 2;
        int textY = y + (DIAMETRO_NODO + fm.getAscent()) / 2 - 2;

        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(idStr, textX + 1, textY + 1);

        g2d.setColor(COLOR_TEXTO);
        g2d.drawString(idStr, textX, textY);
    }

    public static void dibujarCamino(Graphics2D g2d, Grafo grafo, List<Integer> camino, Color colorCamino) {
        if (camino == null || camino.size() < 2) {
            return;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < camino.size() - 1; i++) {
            Nodo nodo1 = grafo.getNodo(camino.get(i));
            Nodo nodo2 = grafo.getNodo(camino.get(i + 1));
            int peso = grafo.getPeso(nodo1.getId(), nodo2.getId());

            dibujarArista(g2d, nodo1, nodo2, peso, colorCamino);
        }

        for (int idNodo : camino) {
            Nodo nodo = grafo.getNodo(idNodo);
            dibujarNodo(g2d, nodo, colorCamino);
        }
    }

    public static void dibujarNodoSeleccionado(Graphics2D g2d, Nodo nodo) {
        dibujarNodo(g2d, nodo, COLOR_SELECCIONADO);
    }

    public static Color getColorCamino() {
        return COLOR_CAMINO;
    }
}
