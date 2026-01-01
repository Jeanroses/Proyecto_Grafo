package com.grafo.algoritmos;

import com.grafo.model.Arista;
import com.grafo.model.ResultadoAlgoritmo;
import java.util.List;

/**
 * Interface para algoritmos de árbol de recubrimiento mínimo
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public interface AlgoritmoArbolMinimo {
    ResultadoAlgoritmo ejecutar(int nodoOrigen);

    List<Arista> getAristasSeleccionadas();

    String getNombre();
}
