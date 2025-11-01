package com.grafo.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Representa un estado del grafo
 * Para implementar Deshacer/Rehacer
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class EstadoGrafo implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final List<NodoData> nodos;
    private final List<AristaData> aristas;

    public EstadoGrafo(Grafo grafo) {
        this.nodos = new ArrayList<>();
        this.aristas = new ArrayList<>();
        capturarEstado(grafo);
    }

    private void capturarEstado(Grafo grafo) {

        for (Nodo nodo : grafo.getNodos()) {
            nodos.add(new NodoData(nodo.getId(), nodo.getX(), nodo.getY()));
        }

        int[][] matrizAdy = grafo.getMatrizAdyacencia();
        int[][] matrizPesos = grafo.getMatrizPesos();
        int totalNodos = grafo.getContadorNodos();

        for (int i = 0; i < totalNodos; i++) {
            for (int j = i + 1; j < totalNodos; j++) {
                if (matrizAdy[i][j] == 1) {
                    aristas.add(new AristaData(i, j, matrizPesos[i][j]));
                }
            }
        }
    }

    public void restaurarEstado(Grafo grafo) {
        grafo.limpiar();

        for (NodoData nodoData : nodos) {
            grafo.agregarNodo(nodoData.x, nodoData.y);
        }

        for (AristaData aristaData : aristas) {
            grafo.agregarArista(aristaData.origen, aristaData.destino, aristaData.peso);
        }
    }

    private static class NodoData implements Serializable {
        @Serial private static final long serialVersionUID = 1L;
        final int id;
        final int x;
        final int y;

        NodoData(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    private static class AristaData implements Serializable {
        @Serial private static final long serialVersionUID = 1L;
        final int origen;
        final int destino;
        final int peso;

        AristaData(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }
    }
}
