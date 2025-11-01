package com.grafo.algoritmos;

import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo DFS (Depth-First Search) - Búsqueda en Profundidad
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoDFS {
    private final Grafo grafo;
    private boolean[] visitados;
    private int[] predecesores;
    private int nodoDestino;
    private boolean encontrado;

    public AlgoritmoDFS(Grafo grafo) {
        this.grafo = grafo;
    }

    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, "DFS");
        }

        this.visitados = new boolean[totalNodos];
        this.predecesores = new int[totalNodos];
        this.nodoDestino = nodoDestino;
        this.encontrado = false;

        Arrays.fill(predecesores, -1);

        dfsRecursivo(nodoOrigen);

        if (!encontrado) {
            return ResultadoAlgoritmo.sinCamino("DFS");
        }

        List<Integer> camino = reconstruirCamino(nodoOrigen, nodoDestino);
        int distancia = calcularDistanciaTotal(camino);

        return new ResultadoAlgoritmo(camino, distancia, "DFS");
    }

    private void dfsRecursivo(int nodoActual) {
        if (encontrado) {
            return;
        }

        visitados[nodoActual] = true;

        if (nodoActual == nodoDestino) {
            encontrado = true;
            return;
        }

        List<Integer> vecinos = grafo.getVecinos(nodoActual);
        for (int vecino : vecinos) {
            if (!visitados[vecino]) {
                predecesores[vecino] = nodoActual;
                dfsRecursivo(vecino);
            }
        }
    }

    private List<Integer> reconstruirCamino(int origen, int destino) {
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
