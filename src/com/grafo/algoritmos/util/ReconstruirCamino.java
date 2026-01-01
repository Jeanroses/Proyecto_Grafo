package com.grafo.algoritmos.util;

import com.grafo.model.Grafo;
import java.util.*;

/**
 * Utilidad para reconstruir caminos y calcular distancias
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public final class ReconstruirCamino {

    private ReconstruirCamino() {
    }

    public static List<Integer> desde(int[] predecesores, int origen, int destino) {
        final Deque<Integer> camino = new ArrayDeque<>();
        int actual = destino;

        while (actual != -1) {
            camino.addFirst(actual);
            if (actual == origen) {
                break;
            }
            actual = predecesores[actual];
        }

        return new ArrayList<>(camino);
    }

    public static int calcularDistancia(Grafo grafo, List<Integer> camino) {
        if (camino == null || camino.size() < 2) {
            return 0;
        }

        int distancia = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            distancia += grafo.getPeso(camino.get(i), camino.get(i + 1));
        }
        return distancia;
    }
}
