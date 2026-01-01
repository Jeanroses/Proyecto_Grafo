package com.grafo.algoritmos;

import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo A* para encontrar el camino más corto
 * usando heurística de distancia euclidiana
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoAStar {
    private final Grafo grafo;

    public AlgoritmoAStar(Grafo grafo) {
        this.grafo = grafo;
    }

    public ResultadoAlgoritmo ejecutar(int nodoOrigen, int nodoDestino) {
        int totalNodos = grafo.getContadorNodos();

        if (nodoOrigen == nodoDestino) {
            return new ResultadoAlgoritmo(List.of(nodoOrigen), 0, "A*");
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
            Comparator.comparingDouble(n -> fScore[n])
        );
        abiertos.add(nodoOrigen);

        while (!abiertos.isEmpty()) {
            int actual = abiertos.poll();

            if (actual == nodoDestino) {
                List<Integer> camino = reconstruirCamino(predecesores, nodoOrigen, nodoDestino);
                int distanciaTotal = (int) Math.round(gScore[nodoDestino]);
                return new ResultadoAlgoritmo(camino, distanciaTotal, "A*");
            }

            if (cerrado[actual]) {
                continue;
            }
            cerrado[actual] = true;

            List<Integer> vecinos = grafo.getVecinos(actual);
            for (int vecino : vecinos) {
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

        return ResultadoAlgoritmo.sinCamino("A*");
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
