package com.grafo.model;

/**
 * Record inmutable que representa una posición 2D en el canvas
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public record Posicion(int x, int y) {

    public double distanciaA(Posicion otra) {
        double dx = otra.x - this.x;
        double dy = otra.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Posicion desplazar(int dx, int dy) {
        return new Posicion(this.x + dx, this.y + dy);
    }
}
