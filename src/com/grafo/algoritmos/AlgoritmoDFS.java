package com.grafo.algoritmos;

import com.grafo.algoritmos.util.ReconstruirCamino;
import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo DFS (Depth-First Search)
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoDFS implements AlgoritmoBusqueda {

    private static final String NOMBRE = "DFS";
    private final Grafo grafo;

    public AlgoritmoDFS(Grafo grafo) {
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
        Arrays.fill(predecesores, -1);

        boolean encontrado = dfsRecursivo(nodoOrigen, nodoDestino, visitados, predecesores);

        if (!encontrado) {
            return ResultadoAlgoritmo.sinCamino(NOMBRE);
        }

        List<Integer> camino = ReconstruirCamino.desde(predecesores, nodoOrigen, nodoDestino);
        int distancia = ReconstruirCamino.calcularDistancia(grafo, camino);

        return new ResultadoAlgoritmo(camino, distancia, NOMBRE);
    }

    private boolean dfsRecursivo(int nodoActual, int nodoDestino,
            boolean[] visitados, int[] predecesores) {
        visitados[nodoActual] = true;

        if (nodoActual == nodoDestino) {
            return true;
        }

        for (int vecino : grafo.getVecinos(nodoActual)) {
            if (!visitados[vecino]) {
                predecesores[vecino] = nodoActual;
                if (dfsRecursivo(vecino, nodoDestino, visitados, predecesores)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
