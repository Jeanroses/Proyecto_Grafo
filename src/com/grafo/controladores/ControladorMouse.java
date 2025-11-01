package com.grafo.controladores;

import com.grafo.model.Grafo;
import com.grafo.ui.PanelGrafo;
import com.grafo.util.GestorHistorial;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Consumer;

/**
 * Controlador para los eventos del mouse en el panel de grafo
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class ControladorMouse {

    private final Grafo grafo;
    private final PanelGrafo panelGrafo;
    private final GestorHistorial gestorHistorial;
    private final Consumer<String> actualizadorEstado;

    private Integer nodoArrastrado = null;
    private int offsetX = 0;
    private int offsetY = 0;
    private boolean animacionesHabilitadas = true;

    private Consumer<Point> callbackCrearNodo;
    private Consumer<Point> callbackClickDerecho;

    public ControladorMouse(Grafo grafo, PanelGrafo panelGrafo, GestorHistorial gestorHistorial,
                           Consumer<String> actualizadorEstado) {
        this.grafo = grafo;
        this.panelGrafo = panelGrafo;
        this.gestorHistorial = gestorHistorial;
        this.actualizadorEstado = actualizadorEstado;
    }

    public void setCallbackCrearNodo(Consumer<Point> callback) {
        this.callbackCrearNodo = callback;
    }

    public void setCallbackClickDerecho(Consumer<Point> callback) {
        this.callbackClickDerecho = callback;
    }

    public void configurarEventos() {
        panelGrafo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final Point coordAjustadas = ajustarCoordenadasZoom(e.getX(), e.getY());

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.isControlDown()) {
                        iniciarArrastre(coordAjustadas.x, coordAjustadas.y);
                    } else {
                        if (callbackCrearNodo != null) {
                            callbackCrearNodo.accept(coordAjustadas);
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (callbackClickDerecho != null) {
                        callbackClickDerecho.accept(coordAjustadas);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finalizarArrastre();
            }
        });

        panelGrafo.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (nodoArrastrado != null) {
                    final Point coordAjustadas = ajustarCoordenadasZoom(e.getX(), e.getY());
                    moverNodo(coordAjustadas.x, coordAjustadas.y);
                }
            }
        });
    }

    private Point ajustarCoordenadasZoom(int x, int y) {
        final double escala = panelGrafo.getEscalaZoom();
        final int xAjustado = (int) (x / escala);
        final int yAjustado = (int) (y / escala);
        return new Point(xAjustado, yAjustado);
    }

    private void iniciarArrastre(int x, int y) {
        var nodoOpt = grafo.obtenerNodoEnPosicion(x, y);
        if (nodoOpt.isPresent()) {
            gestorHistorial.guardarEstado(grafo);

            nodoArrastrado = nodoOpt.get().getId();
            var nodo = grafo.getNodo(nodoArrastrado);
            offsetX = x - nodo.getX();
            offsetY = y - nodo.getY();
            panelGrafo.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            actualizadorEstado.accept("Moviendo nodo " + nodoArrastrado + " (Ctrl+Click)");
        }
    }

    private void moverNodo(int x, int y) {
        var nodo = grafo.getNodo(nodoArrastrado);
        if (nodo != null) {
            int nuevaX = x - offsetX;
            int nuevaY = y - offsetY;

            final double escala = panelGrafo.getEscalaZoom();
            final int anchoReal = (int) (panelGrafo.getWidth() / escala);
            final int altoReal = (int) (panelGrafo.getHeight() / escala);

            nuevaX = Math.max(10, Math.min(nuevaX, anchoReal - 50));
            nuevaY = Math.max(10, Math.min(nuevaY, altoReal - 50));

            if (animacionesHabilitadas) {
                animarMovimiento(nodo, nuevaX, nuevaY);
            } else {
                nodo.setX(nuevaX);
                nodo.setY(nuevaY);
            }
            panelGrafo.repaint();
        }
    }

    private void finalizarArrastre() {
        if (nodoArrastrado != null) {
            nodoArrastrado = null;
            panelGrafo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            actualizadorEstado.accept("Nodo movido - Listo");
        }
    }

    private void animarMovimiento(com.grafo.model.Nodo nodo, int targetX, int targetY) {
        final int currentX = nodo.getX();
        final int currentY = nodo.getY();
        final int deltaX = targetX - currentX;
        final int deltaY = targetY - currentY;

        if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
            nodo.setX(currentX + deltaX / 2);
            nodo.setY(currentY + deltaY / 2);
        } else {
            nodo.setX(targetX);
            nodo.setY(targetY);
        }
    }

    @SuppressWarnings("unused")
    public void setAnimacionesHabilitadas(boolean habilitadas) {
        this.animacionesHabilitadas = habilitadas;
    }
}
