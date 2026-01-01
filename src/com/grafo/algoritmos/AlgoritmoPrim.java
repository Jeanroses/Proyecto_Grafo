package com.grafo.algoritmos;

import com.grafo.model.*;
import java.util.*;

/**
 * Implementación del algoritmo de Prim para el árbol de recubrimiento mínimo
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class AlgoritmoPrim implements AlgoritmoArbolMinimo {

    private static final String NOMBRE = "Prim";
    private final Grafo grafo;
    private List<Arista> aristasSeleccionadas;

    public AlgoritmoPrim(Grafo grafo) {
        this.grafo = Objects.requireNonNull(grafo, "El grafo no puede ser null");
        this.aristasSeleccionadas = new ArrayList<>();
    }

    @Override
    public ResultadoAlgoritmo ejecutar(int nodoOrigen) {
        int totalNodos = grafo.getContadorNodos();

        if (totalNodos == 0) {
            return ResultadoAlgoritmo.sinCamino(NOMBRE);
        }

        aristasSeleccionadas = new ArrayList<>();
        int pesoTotal = ejecutarAlgoritmo(nodoOrigen, totalNodos);

        List<Integer> camino = construirCaminoDesdeAristas();
        return new ResultadoAlgoritmo(camino, pesoTotal, NOMBRE);
    }

    private int ejecutarAlgoritmo(int nodoOrigen, int totalNodos) {
        Set<Integer> enArbol = new LinkedHashSet<>();
        int pesoTotal = 0;

        enArbol.add(nodoOrigen);

        while (enArbol.size() < totalNodos) {
            Arista mejorArista = encontrarMejorArista(enArbol);

            if (mejorArista == null) {
                break;
            }

            enArbol.add(mejorArista.destino());
            aristasSeleccionadas.add(mejorArista);
            pesoTotal += mejorArista.peso();
        }

        return pesoTotal;
    }

    private Arista encontrarMejorArista(Set<Integer> enArbol) {
        Arista mejor = null;
        int menorPeso = Integer.MAX_VALUE;

        for (int nodoArbol : enArbol) {
            for (int vecino : grafo.getVecinos(nodoArbol)) {
                if (!enArbol.contains(vecino)) {
                    int peso = grafo.getPeso(nodoArbol, vecino);
                    if (peso > 0 && peso < menorPeso) {
                        menorPeso = peso;
                        mejor = new Arista(nodoArbol, vecino, peso);
                    }
                }
            }
        }

        return mejor;
    }

    private List<Integer> construirCaminoDesdeAristas() {
        Set<Integer> nodos = new LinkedHashSet<>();
        for (Arista arista : aristasSeleccionadas) {
            nodos.add(arista.origen());
            nodos.add(arista.destino());
        }
        return new ArrayList<>(nodos);
    }

    @Override
    public List<Arista> getAristasSeleccionadas() {
        return Collections.unmodifiableList(aristasSeleccionadas);
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
