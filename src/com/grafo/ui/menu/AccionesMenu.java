package com.grafo.ui.menu;

/**
 * Interface para las acciones del menú
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public interface AccionesMenu {
    void limpiarTodo();

    void abrirGrafo();

    void guardarGrafo();

    void exportarPNG();

    void cargarEjemplo();

    void deshacer();

    void rehacer();

    void crearArista();

    void eliminarArista();

    void eliminarTodasAristas();

    void eliminarNodo();

    void ejecutarDijkstra();

    void ejecutarBFS();

    void ejecutarDFS();

    void ejecutarAStar();

    void ejecutarPrim();

    void toggleModoOscuro();

    void zoomIn();

    void zoomOut();

    void zoomReset();

    void mostrarMatrizAdyacencia();

    void mostrarMatrizPesos();

    void mostrarAcercaDe();
}
