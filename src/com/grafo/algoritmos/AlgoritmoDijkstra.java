package com.grafo.algoritmos;

import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo de Dijkstra para encontrar el camino más corto
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoDijkstra {
    private final Grafo grafo;

    public AlgoritmoDijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, "Dijkstra");
        }

        int[] distancias = new int[totalNodos];
        int[] predecesores = new int[totalNodos];
        boolean[] visitados = new boolean[totalNodos];
        boolean[] etiquetados = new boolean[totalNodos];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesores, -1);
        distancias[nodoOrigen] = 0;

        int nodoActual = nodoOrigen;
        int nodosEtiquetados = 0;

        while (nodosEtiquetados < totalNodos) {
            etiquetados[nodoActual] = true;
            nodosEtiquetados++;

            List<Integer> vecinos = grafo.getVecinos(nodoActual);
            for (int vecino : vecinos) {
                if (!etiquetados[vecino]) {
                    int nuevaDistancia = distancias[nodoActual] + grafo.getPeso(nodoActual, vecino);

                    if (!visitados[vecino] || nuevaDistancia < distancias[vecino]) {
                        distancias[vecino] = nuevaDistancia;
                        predecesores[vecino] = nodoActual;
                        visitados[vecino] = true;
                    }
                }
            }

            int menorDistancia = Integer.MAX_VALUE;
            int siguienteNodo = -1;

            for (int i = 0; i < totalNodos; i++) {
                if (visitados[i] && !etiquetados[i] && distancias[i] < menorDistancia) {
                    menorDistancia = distancias[i];
                    siguienteNodo = i;
                }
            }

            if (siguienteNodo == -1) {
                break;
            }

            nodoActual = siguienteNodo;
        }

        if (!visitados[nodoDestino]) {
            return ResultadoAlgoritmo.sinCamino("Dijkstra");
        }

        List<Integer> camino = reconstruirCamino(predecesores, nodoOrigen, nodoDestino);
        return new ResultadoAlgoritmo(camino, distancias[nodoDestino], "Dijkstra");
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
}
