package com.grafo.controladores;

import com.grafo.algoritmos.*;
import com.grafo.model.Grafo;
import com.grafo.model.ResultadoAlgoritmo;
import com.grafo.ui.PanelGrafo;
import com.grafo.util.RenderizadorGrafo;
import com.grafo.util.ValidadorEntrada;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controlador para la ejecución de algoritmos
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class ControladorAlgoritmos {

    private static final int MIN_NODOS_CAMINO = 2;

    private final Grafo grafo;
    private final PanelGrafo panelGrafo;
    private final Component parentComponent;
    private final Consumer<String> actualizadorDistancia;
    private final Consumer<String> actualizadorEstado;

    public ControladorAlgoritmos(Grafo grafo, PanelGrafo panelGrafo, Component parentComponent,
                                Consumer<String> actualizadorDistancia, Consumer<String> actualizadorEstado) {
        this.grafo = grafo;
        this.panelGrafo = panelGrafo;
        this.parentComponent = parentComponent;
        this.actualizadorDistancia = actualizadorDistancia;
        this.actualizadorEstado = actualizadorEstado;
    }

    public void ejecutarDijkstra() {
        if (!validarNodosMinimos()) return;

        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo origen:", grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo destino:", grafo.getContadorNodos());

            var dijkstra = new AlgoritmoDijkstra(grafo);
            var resultado = dijkstra.ejecutar(origen, destino);

            mostrarResultado(resultado);
        } catch (IllegalStateException ignored) {
        }
    }

    public void ejecutarBFS() {
        if (!validarNodosMinimos()) return;

        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo origen:", grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo destino:", grafo.getContadorNodos());

            var bfs = new AlgoritmoBFS(grafo);
            var resultado = bfs.ejecutar(origen, destino);

            mostrarResultado(resultado);
        } catch (IllegalStateException ignored) {
        }
    }

    public void ejecutarDFS() {
        if (!validarNodosMinimos()) return;

        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo origen:", grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo destino:", grafo.getContadorNodos());

            var dfs = new AlgoritmoDFS(grafo);
            var resultado = dfs.ejecutar(origen, destino);

            mostrarResultado(resultado);
        } catch (IllegalStateException ignored) {
        }
    }

    public void ejecutarPrim() {
        if (!validarNodosMinimos()) return;

        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(
                parentComponent, "Ingrese el nodo origen:", grafo.getContadorNodos());

            var prim = new AlgoritmoPrim(grafo);
            var resultado = prim.ejecutar(origen);

            mostrarResultadoPrim(resultado, prim.getAristasSeleccionadas(origen));
        } catch (IllegalStateException ignored) {
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean validarNodosMinimos() {
        if (grafo.getContadorNodos() < MIN_NODOS_CAMINO) {
            JOptionPane.showMessageDialog(parentComponent,
                "Debe haber al menos " + MIN_NODOS_CAMINO + " nodos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void mostrarResultado(ResultadoAlgoritmo resultado) {
        if (!resultado.existeCamino()) {
            JOptionPane.showMessageDialog(parentComponent,
                "No se encontró un camino entre los nodos seleccionados",
                resultado.getNombreAlgoritmo(),
                JOptionPane.WARNING_MESSAGE);
            actualizadorDistancia.accept("N/A");
            actualizadorEstado.accept("No se encontró camino");
            return;
        }

        panelGrafo.setCaminoResaltado(resultado.getCamino());
        panelGrafo.setColorCamino(RenderizadorGrafo.getColorCamino());
        panelGrafo.repaint();

        actualizadorDistancia.accept(String.valueOf(resultado.getDistanciaTotal()));

        List<Integer> caminoBase1 = resultado.getCamino().stream()
            .map(id -> id + 1)
            .toList();

        String mensaje = String.format(
            "Algoritmo: %s\n\nCamino encontrado: %s\n\nDistancia total: %d",
            resultado.getNombreAlgoritmo(),
            caminoBase1,
            resultado.getDistanciaTotal()
        );

        JOptionPane.showMessageDialog(parentComponent,
            mensaje,
            "Resultado - " + resultado.getNombreAlgoritmo(),
            JOptionPane.INFORMATION_MESSAGE);

        actualizadorEstado.accept(resultado.getNombreAlgoritmo() + " ejecutado - Distancia: " + resultado.getDistanciaTotal());
    }

    private void mostrarResultadoPrim(ResultadoAlgoritmo resultado, List<int[]> aristas) {
        if (!resultado.existeCamino()) {
            JOptionPane.showMessageDialog(parentComponent,
                "No se pudo construir el árbol de recubrimiento mínimo",
                "Prim",
                JOptionPane.WARNING_MESSAGE);
            actualizadorDistancia.accept("N/A");
            return;
        }

        panelGrafo.setCaminoResaltado(resultado.getCamino());
        panelGrafo.setColorCamino(new Color(231, 76, 60));
        panelGrafo.repaint();

        actualizadorDistancia.accept(String.valueOf(resultado.getDistanciaTotal()));

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Algoritmo: Prim (Árbol de Recubrimiento Mínimo)\n\n");
        mensaje.append("Aristas seleccionadas:\n");
        for (int[] arista : aristas) {
            mensaje.append(String.format("  %d → %d (peso: %d)\n", arista[0] + 1, arista[1] + 1, arista[2]));
        }
        mensaje.append(String.format("\nPeso total: %d", resultado.getDistanciaTotal()));

        JOptionPane.showMessageDialog(parentComponent,
            mensaje.toString(),
            "Resultado - Prim",
            JOptionPane.INFORMATION_MESSAGE);

        actualizadorEstado.accept("Prim ejecutado - Peso total: " + resultado.getDistanciaTotal());
    }
}
