package com.grafo.model;

/**
 * Record inmutable que representa una arista del grafo
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public record Arista(int origen, int destino, int peso) {

    public Arista {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
    }

    @Override
    public String toString() {
        return String.format("%d → %d (peso: %d)", origen + 1, destino + 1, peso);
    }
}
