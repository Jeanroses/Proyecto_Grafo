package com.grafo.algoritmos;

import com.grafo.algoritmos.util.ReconstruirCamino;
import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo BFS (Breadth-First Search)
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoBFS implements AlgoritmoBusqueda {

    private static final String NOMBRE = "BFS";
    private final Grafo grafo;

    public AlgoritmoBFS(Grafo grafo) {
        this.grafo = Objects.requireNonNull(grafo, "El grafo no puede ser null");
    }

    @Override
    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, NOMBRE);
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

            for (int vecino : grafo.getVecinos(nodoActual)) {
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    predecesores[vecino] = nodoActual;
                    cola.offer(vecino);
                }
            }
        }

        if (!visitados[nodoDestino]) {
            return ResultadoAlgoritmo.sinCamino(NOMBRE);
        }

        List<Integer> camino = ReconstruirCamino.desde(predecesores, nodoOrigen, nodoDestino);
        int distancia = ReconstruirCamino.calcularDistancia(grafo, camino);

        return new ResultadoAlgoritmo(camino, distancia, NOMBRE);
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
