package com.grafo.algoritmos;

import com.grafo.model.ResultadoAlgoritmo;

/**
 * Interface para algoritmos de búsqueda de camino entre dos nodos
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public interface AlgoritmoBusqueda {
    ResultadoAlgoritmo ejecutar(int origen, int destino);

    String getNombre();
}
