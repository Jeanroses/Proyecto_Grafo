package com.grafo.algoritmos;

import com.grafo.algoritmos.util.ReconstruirCamino;
import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo de Dijkstra para encontrar el camino más corto
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoDijkstra implements AlgoritmoBusqueda {

    private static final String NOMBRE = "Dijkstra";
    private final Grafo grafo;

    public AlgoritmoDijkstra(Grafo grafo) {
        this.grafo = Objects.requireNonNull(grafo, "El grafo no puede ser null");
    }

    @Override
    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, NOMBRE);
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

            for (int vecino : grafo.getVecinos(nodoActual)) {
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
            return ResultadoAlgoritmo.sinCamino(NOMBRE);
        }

        List<Integer> camino = ReconstruirCamino.desde(predecesores, nodoOrigen, nodoDestino);
        return new ResultadoAlgoritmo(camino, distancias[nodoDestino], NOMBRE);
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
