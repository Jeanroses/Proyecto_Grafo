package com.grafo.algoritmos;

import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo de Prim para encontrar el árbol de recubrimiento mínimo
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoPrim {
    private final Grafo grafo;
    private List<int[]> aristasSeleccionadas;

    public AlgoritmoPrim(Grafo grafo) {
        this.grafo = grafo;
        this.aristasSeleccionadas = new ArrayList<>();
    }

    public ResultadoAlgoritmo ejecutar(int nodoOrigen) {
        int totalNodos = grafo.getContadorNodos();

        if (totalNodos == 0) {
            return ResultadoAlgoritmo.sinCamino("Prim");
        }

        aristasSeleccionadas = new ArrayList<>();

        int pesoTotal = ejecutarAlgoritmo(nodoOrigen, totalNodos);

        List<Integer> camino = construirCaminoDesdeAristas(aristasSeleccionadas);
        return new ResultadoAlgoritmo(camino, pesoTotal, "Prim");
    }

    private int ejecutarAlgoritmo(int nodoOrigen, int totalNodos) {
        List<Integer> nodosEnArbol = new ArrayList<>();
        Set<Integer> enArbol = new HashSet<>();
        int pesoTotal = 0;

        nodosEnArbol.add(nodoOrigen);
        enArbol.add(nodoOrigen);

        while (nodosEnArbol.size() < totalNodos) {
            int mejorOrigen = -1;
            int mejorDestino = -1;
            int menorPeso = Integer.MAX_VALUE;
            boolean encontroArista = false;

            for (int nodoArbol : nodosEnArbol) {
                List<Integer> vecinos = grafo.getVecinos(nodoArbol);

                for (int vecino : vecinos) {
                    if (!enArbol.contains(vecino)) {
                        int peso = grafo.getPeso(nodoArbol, vecino);
                        if (peso < menorPeso && peso > 0) {
                            menorPeso = peso;
                            mejorOrigen = nodoArbol;
                            mejorDestino = vecino;
                            encontroArista = true;
                        }
                    }
                }
            }

            if (!encontroArista) {
                break;
            }

            nodosEnArbol.add(mejorDestino);
            enArbol.add(mejorDestino);
            aristasSeleccionadas.add(new int[]{mejorOrigen, mejorDestino, menorPeso});
            pesoTotal += menorPeso;
        }

        return pesoTotal;
    }

    private List<Integer> construirCaminoDesdeAristas(List<int[]> aristas) {
        Set<Integer> nodos = new LinkedHashSet<>();
        for (int[] arista : aristas) {
            nodos.add(arista[0]);
            nodos.add(arista[1]);
        }
        return new ArrayList<>(nodos);
    }

    public List<int[]> getAristasSeleccionadas(int nodoOrigen) {
        if (aristasSeleccionadas.isEmpty()) {
            ejecutar(nodoOrigen);
        }

        List<int[]> copia = new ArrayList<>();
        for (int[] arista : aristasSeleccionadas) {
            copia.add(arista.clone());
        }
        return copia;
    }
}
