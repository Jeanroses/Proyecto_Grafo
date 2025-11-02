package com.grafo.model;

import java.util.*;

/**
 * Representación de un grafo no dirigido utilizando matrices de adyacencia y pesos
 * @author Jean Paul Purihuaman De La Cruz
 */

public class Grafo {
    private static final int MAX_NODOS = 100;
    private static final int RADIO_NODO_RENDER = 20;
    private static final int RADIO_DETECCION = 25;

    private final int[][] matrizAdyacencia;
    private final int[][] matrizPesos;
    private final List<Nodo> nodos;
    private int contadorNodos;

    public Grafo() {
        this.matrizAdyacencia = new int[MAX_NODOS][MAX_NODOS];
        this.matrizPesos = new int[MAX_NODOS][MAX_NODOS];
        this.nodos = new ArrayList<>();
        this.contadorNodos = 0;
    }

    public int agregarNodo(int x, int y) {
        if (contadorNodos >= MAX_NODOS) {
            throw new IllegalStateException("Se ha alcanzado el máximo de nodos permitidos");
        }

        Nodo nodo = new Nodo(contadorNodos, x, y);
        nodos.add(nodo);
        return contadorNodos++;
    }

    public void agregarArista(int origen, int destino, int peso) {
        validarNodo(origen);
        validarNodo(destino);

        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }

        matrizAdyacencia[origen][destino] = 1;
        matrizAdyacencia[destino][origen] = 1;
        matrizPesos[origen][destino] = peso;
        matrizPesos[destino][origen] = peso;
    }

    public void eliminarArista(int origen, int destino) {
        validarNodo(origen);
        validarNodo(destino);

        matrizAdyacencia[origen][destino] = 0;
        matrizAdyacencia[destino][origen] = 0;
        matrizPesos[origen][destino] = 0;
        matrizPesos[destino][origen] = 0;
    }

    public void limpiarAristas() {
        for (int i = 0; i < MAX_NODOS; i++) {
            for (int j = 0; j < MAX_NODOS; j++) {
                matrizAdyacencia[i][j] = 0;
                matrizPesos[i][j] = 0;
            }
        }
    }

    public void eliminarNodo(int id) {
        validarNodo(id);

        for (int i = id; i < contadorNodos - 1; i++) {
            System.arraycopy(matrizAdyacencia[i + 1], 0, matrizAdyacencia[i], 0, MAX_NODOS);
            System.arraycopy(matrizPesos[i + 1], 0, matrizPesos[i], 0, MAX_NODOS);
        }

        for (int i = 0; i < contadorNodos - 1; i++) {
            for (int j = id; j < contadorNodos - 1; j++) {
                matrizAdyacencia[i][j] = matrizAdyacencia[i][j + 1];
                matrizPesos[i][j] = matrizPesos[i][j + 1];
            }
        }

        int ultimaFila = contadorNodos - 1;
        for (int i = 0; i < MAX_NODOS; i++) {
            matrizAdyacencia[ultimaFila][i] = 0;
            matrizAdyacencia[i][ultimaFila] = 0;
            matrizPesos[ultimaFila][i] = 0;
            matrizPesos[i][ultimaFila] = 0;
        }

        nodos.remove(id);
        for (int i = id; i < nodos.size(); i++) {
            nodos.get(i).setId(i);
        }

        contadorNodos--;
    }

    public Optional<Nodo> obtenerNodoEnPosicion(int x, int y) {
        for (Nodo nodo : nodos) {
            int dx = x - nodo.getX() - RADIO_NODO_RENDER;
            int dy = y - nodo.getY() - RADIO_NODO_RENDER;
            double distancia = Math.sqrt(dx * dx + dy * dy);

            if (distancia <= RADIO_DETECCION) {
                return Optional.of(nodo);
            }
        }
        return Optional.empty();
    }

    public List<Integer> getVecinos(int nodo) {
        validarNodo(nodo);
        List<Integer> vecinos = new ArrayList<>();
        for (int i = 0; i < contadorNodos; i++) {
            if (matrizAdyacencia[nodo][i] == 1) {
                vecinos.add(i);
            }
        }
        return vecinos;
    }

    public boolean existeArista(int origen, int destino) {
        return matrizAdyacencia[origen][destino] == 1;
    }

    public int getPeso(int origen, int destino) {
        return matrizPesos[origen][destino];
    }

    public void limpiar() {
        nodos.clear();
        contadorNodos = 0;
        limpiarAristas();
    }

    public void cargarEjemplo() {
        limpiar();

        int[][] coordenadas = {
            {150, 50}, {350, 50}, {100, 150}, {250, 150},
            {400, 150}, {150, 250}, {350, 250}, {250, 350},
            {450, 200}, {50, 300}
        };

        for (int[] coord : coordenadas) {
            agregarNodo(coord[0], coord[1]);
        }

        agregarArista(0, 1, 120);
        agregarArista(0, 2, 85);
        agregarArista(0, 3, 95);
        agregarArista(1, 3, 70);
        agregarArista(1, 4, 110);
        agregarArista(2, 3, 140);
        agregarArista(2, 5, 90);
        agregarArista(2, 9, 65);
        agregarArista(3, 5, 105);
        agregarArista(3, 6, 125);
        agregarArista(4, 6, 180);
        agregarArista(4, 8, 75);
        agregarArista(5, 7, 160);
        agregarArista(5, 9, 200);
        agregarArista(6, 7, 95);
        agregarArista(6, 8, 135);
        agregarArista(7, 9, 220);
    }

    private void validarNodo(int nodo) {
        if (nodo < 0 || nodo >= contadorNodos) {
            throw new IllegalArgumentException("Nodo inválido: " + nodo);
        }
    }

    public int getContadorNodos() {
        return contadorNodos;
    }

    public List<Nodo> getNodos() {
        return Collections.unmodifiableList(nodos);
    }

    public Nodo getNodo(int id) {
        if (id >= 0 && id < nodos.size()) {
            return nodos.get(id);
        }
        return null;
    }

    public int[][] getMatrizAdyacencia() {
        int[][] copia = new int[MAX_NODOS][MAX_NODOS];
        for (int i = 0; i < MAX_NODOS; i++) {
            System.arraycopy(matrizAdyacencia[i], 0, copia[i], 0, MAX_NODOS);
        }
        return copia;
    }

    public int[][] getMatrizPesos() {
        int[][] copia = new int[MAX_NODOS][MAX_NODOS];
        for (int i = 0; i < MAX_NODOS; i++) {
            System.arraycopy(matrizPesos[i], 0, copia[i], 0, MAX_NODOS);
        }
        return copia;
    }
}
