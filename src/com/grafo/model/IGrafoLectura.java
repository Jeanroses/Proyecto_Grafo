package com.grafo.model;

import java.util.List;
import java.util.Optional;

/**
 * Interface de solo lectura para operaciones con grafos
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public interface IGrafoLectura {
    int getContadorNodos();

    List<Nodo> getNodos();

    Nodo getNodo(int id);

    List<Integer> getVecinos(int nodo);

    int getPeso(int origen, int destino);

    boolean existeArista(int origen, int destino);

    Optional<Nodo> obtenerNodoEnPosicion(int x, int y);

    int[][] getMatrizAdyacencia();

    int[][] getMatrizPesos();
}
