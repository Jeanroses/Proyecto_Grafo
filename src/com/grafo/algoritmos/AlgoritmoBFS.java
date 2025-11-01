package com.grafo.algoritmos;

import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo BFS (Breadth-First Search) - Búsqueda en Anchura
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoBFS {
    private final Grafo grafo;

    public AlgoritmoBFS(Grafo grafo) {
        this.grafo = grafo;
    }

    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, "BFS");
        }

        boolean[] visitados = new boolean[totalNodos];
        int[] predecesores = new int[totalNodos];
        Queue<Integer> cola = new ArrayDeque<>();

        Arrays.fill(predecesores, -1);
        visitados[nodoOrigen] = true;
        cola.offer(nodoOrigen);

        while (!cola.isEmpty()) {
            int nodoActual = cola.poll();

            if (nodoActual == nodoDestino) {
                break;
            }

            List<Integer> vecinos = grafo.getVecinos(nodoActual);
            for (int vecino : vecinos) {
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    predecesores[vecino] = nodoActual;
                    cola.offer(vecino);
                }
            }
        }

        if (!visitados[nodoDestino]) {
            return ResultadoAlgoritmo.sinCamino("BFS");
        }

        List<Integer> camino = reconstruirCamino(predecesores, nodoOrigen, nodoDestino);
        int distancia = calcularDistanciaTotal(camino);

        return new ResultadoAlgoritmo(camino, distancia, "BFS");
    }

    private List<Integer> reconstruirCamino(int[] predecesores, int origen, int destino) {
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

    private int calcularDistanciaTotal(List<Integer> camino) {
        int distancia = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            distancia += grafo.getPeso(camino.get(i), camino.get(i + 1));
        }
        return distancia;
    }
}
