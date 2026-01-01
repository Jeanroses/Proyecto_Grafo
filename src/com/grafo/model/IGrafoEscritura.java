package com.grafo.model;

/**
 * Interface de escritura para modificación del grafo
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public interface IGrafoEscritura {
    int agregarNodo(int x, int y);

    void agregarArista(int origen, int destino, int peso);

    void eliminarArista(int origen, int destino);

    void eliminarNodo(int id);

    void limpiarAristas();

    void limpiar();

    void cargarEjemplo();
}
