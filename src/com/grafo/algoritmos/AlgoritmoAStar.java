package com.grafo.algoritmos;

import com.grafo.algoritmos.util.ReconstruirCamino;
import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo A* con heurística euclidiana
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoAStar implements AlgoritmoBusqueda {

    private static final String NOMBRE = "A*";
    private final Grafo grafo;

    public AlgoritmoAStar(Grafo grafo) {
        this.grafo = Objects.requireNonNull(grafo, "El grafo no puede ser null");
    }

    @Override
    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, NOMBRE);
        }

        double[] gScore = new double[totalNodos];
        double[] fScore = new double[totalNodos];
        int[] predecesores = new int[totalNodos];
        boolean[] cerrado = new boolean[totalNodos];

        Arrays.fill(gScore, Double.MAX_VALUE);
        Arrays.fill(fScore, Double.MAX_VALUE);
        Arrays.fill(predecesores, -1);

        gScore[nodoOrigen] = 0;
        fScore[nodoOrigen] = calcularHeuristica(nodoOrigen, nodoDestino);

        PriorityQueue<Integer> abiertos = new PriorityQueue<>(
                Comparator.comparingDouble(n -> fScore[n]));
        abiertos.add(nodoOrigen);

        while (!abiertos.isEmpty()) {
            int actual = abiertos.poll();

            if (actual == nodoDestino) {
                List<Integer> camino = ReconstruirCamino.desde(predecesores, nodoOrigen, nodoDestino);
                int distanciaTotal = (int) Math.round(gScore[nodoDestino]);
                return new ResultadoAlgoritmo(camino, distanciaTotal, NOMBRE);
            }

            if (cerrado[actual]) {
                continue;
            }
            cerrado[actual] = true;

            for (int vecino : grafo.getVecinos(actual)) {
                if (cerrado[vecino]) {
                    continue;
                }

                double tentativeG = gScore[actual] + grafo.getPeso(actual, vecino);

                if (tentativeG < gScore[vecino]) {
                    predecesores[vecino] = actual;
                    gScore[vecino] = tentativeG;
                    fScore[vecino] = tentativeG + calcularHeuristica(vecino, nodoDestino);
                    abiertos.add(vecino);
                }
            }
        }

        return ResultadoAlgoritmo.sinCamino(NOMBRE);
    }

    private double calcularHeuristica(int nodo, int destino) {
        Nodo n1 = grafo.getNodo(nodo);
        Nodo n2 = grafo.getNodo(destino);

        if (n1 == null || n2 == null) {
            return 0;
        }

        double dx = n2.getX() - n1.getX();
        double dy = n2.getY() - n1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
