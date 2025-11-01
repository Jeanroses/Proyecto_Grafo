package com.grafo.model;

import java.util.List;

/**
 * Clase que encapsula el resultado de un algoritmo de búsqueda
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class ResultadoAlgoritmo {
    private final List<Integer> camino;
    private final int distanciaTotal;
    private final boolean existeCamino;
    private final String nombreAlgoritmo;

    public ResultadoAlgoritmo(List<Integer> camino, int distanciaTotal, String nombreAlgoritmo) {
        this.camino = camino;
        this.distanciaTotal = distanciaTotal;
        this.existeCamino = camino != null && !camino.isEmpty();
        this.nombreAlgoritmo = nombreAlgoritmo;
    }

    public static ResultadoAlgoritmo sinCamino(String nombreAlgoritmo) {
        return new ResultadoAlgoritmo(List.of(), 0, nombreAlgoritmo);
    }

    public List<Integer> getCamino() {
        return camino;
    }

    public int getDistanciaTotal() {
        return distanciaTotal;
    }

    public boolean existeCamino() {
        return existeCamino;
    }

    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    @Override
    public String toString() {
        if (!existeCamino) {
            return nombreAlgoritmo + ": No se encontró camino";
        }
        return String.format("%s: Camino=%s, Distancia=%d",
                nombreAlgoritmo, camino, distanciaTotal);
    }
}

