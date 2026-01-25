package com.grafo.ui;

import com.grafo.controladores.*;
import com.grafo.model.*;
import com.grafo.ui.dialogos.DialogoAcercaDe;
import com.grafo.ui.menu.*;
import com.grafo.ui.paneles.*;
import com.grafo.util.*;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class VentanaPrincipal extends JFrame implements AccionesMenu {

    private final Grafo grafo;
    private final PanelGrafo panelGrafo;
    private final PanelInformacion panelInformacion;
    private final GestorHistorial gestorHistorial;
    private final ControladorMouse controladorMouse;
    private final ControladorAlgoritmos controladorAlgoritmos;
    private final GestorZoom gestorZoom;
    private final GestorTemas gestorTemas;

    private JScrollPane scrollPane;
    private Integer nodoSeleccionado1 = null;
    private Integer nodoSeleccionado2 = null;

    public VentanaPrincipal() {
        ModernUI.applyGlobalFont("Segoe UI", 13);

        this.grafo = new Grafo();
        this.panelGrafo = new PanelGrafo(grafo);
        this.panelInformacion = new PanelInformacion();
        this.gestorHistorial = new GestorHistorial();
        this.gestorZoom = new GestorZoom();
        this.gestorTemas = new GestorTemas();

        this.controladorMouse = new ControladorMouse(grafo, panelGrafo, gestorHistorial, this::actualizarEstado);
        this.controladorAlgoritmos = new ControladorAlgoritmos(
                grafo, panelGrafo, this,
                panelInformacion::actualizarDistancia,
                this::actualizarEstado,
                panelInformacion::actualizarResultado);

        inicializarUI();
        configurarEventos();
    }

    private void inicializarUI() {
        setTitle("Sistema de grafos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1400, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        configurarIconoAplicacion();

        scrollPane = new JScrollPane(panelGrafo);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        PanelControles panelControles = new PanelControles(new PanelControles.AccionesControles() {
            @Override
            public void limpiarTodo() {
                VentanaPrincipal.this.limpiarTodo();
            }

            @Override
            public void repintar() {
                repintarGrafo();
            }

            @Override
            public void cargarEjemplo() {
                VentanaPrincipal.this.cargarEjemplo();
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(panelControles, BorderLayout.WEST);
        add(panelInformacion, BorderLayout.SOUTH);
        setJMenuBar(new MenuBuilder(this).construir());
    }

    private void configurarEventos() {
        controladorMouse.setCallbackCrearNodo(point -> manejarCrearNodo(point.x, point.y));
        controladorMouse.setCallbackClickDerecho(point -> manejarClickDerecho(point.x, point.y));
        controladorMouse.configurarEventos();

        panelGrafo.addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                if (e.getWheelRotation() < 0)
                    zoomIn();
                else
                    zoomOut();
            }
        });
    }

    private void configurarIconoAplicacion() {
        try {
            Image icono = GestorImagenes.cargarIconoAplicacionEscalado(64, 64);
            if (icono != null)
                setIconImage(icono);
        } catch (Exception e) {
            System.err.println("Error al configurar el icono: " + e.getMessage());
        }
    }

    private void manejarCrearNodo(int x, int y) {
        try {
            gestorHistorial.guardarEstado(grafo);
            int id = grafo.agregarNodo(x, y);
            panelGrafo.repaint();
            actualizarEstado("✓ Nodo " + (id + 1) + " creado");
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarClickDerecho(int x, int y) {
        var nodoOpt = grafo.obtenerNodoEnPosicion(x, y);
        if (nodoOpt.isEmpty())
            return;

        int idNodo = nodoOpt.get().getId();

        if (nodoSeleccionado1 == null) {
            nodoSeleccionado1 = idNodo;
            panelGrafo.setNodoSeleccionado1(idNodo);
            panelGrafo.repaint();
            actualizarEstado("Nodo " + (idNodo + 1) + " seleccionado. Seleccione el segundo nodo");
        } else if (nodoSeleccionado2 == null) {
            if (idNodo == nodoSeleccionado1) {
                limpiarSeleccion();
                actualizarEstado("Selección cancelada");
                return;
            }
            nodoSeleccionado2 = idNodo;
            try {
                int peso = ValidadorEntrada.solicitarPeso(this, "Ingrese el peso de la arista:");
                gestorHistorial.guardarEstado(grafo);
                grafo.agregarArista(nodoSeleccionado1, nodoSeleccionado2, peso);
                actualizarEstado("Arista creada: " + (nodoSeleccionado1 + 1) + " → " + (nodoSeleccionado2 + 1)
                        + " (peso: " + peso + ")");
            } catch (IllegalStateException ex) {
                actualizarEstado("Operación cancelada");
            } finally {
                limpiarSeleccion();
            }
        }
    }

    private void limpiarSeleccion() {
        nodoSeleccionado1 = null;
        nodoSeleccionado2 = null;
        panelGrafo.setNodoSeleccionado1(null);
        panelGrafo.repaint();
    }

    private void repintarGrafo() {
        panelGrafo.limpiarResaltado();
        panelGrafo.repaint();
        actualizarEstado("Grafo repintado");
    }

    private void actualizarEstado(String mensaje) {
        panelInformacion.actualizarEstado(mensaje);
    }

    @Override
    public void limpiarTodo() {
        if (DialogosModernos.mostrarDialogoConfirmacion(this, "¿Está seguro de limpiar todo el grafo?",
                "Confirmar Limpieza")) {
            grafo.limpiar();
            gestorHistorial.limpiar();
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            panelInformacion.limpiar();
            limpiarSeleccion();
            actualizarEstado("Grafo limpiado");
        }
    }

    @Override
    public void abrirGrafo() {
        if (GestorArchivos.abrirGrafo(this, grafo)) {
            gestorHistorial.limpiar();
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Grafo cargado con " + grafo.getContadorNodos() + " nodos");
        }
    }

    @Override
    public void guardarGrafo() {
        if (GestorArchivos.guardarGrafo(this, grafo)) {
            actualizarEstado("Grafo guardado exitosamente");
        }
    }

    @Override
    public void exportarPNG() {
        if (GestorImagenes.exportarPNG(this, panelGrafo)) {
            actualizarEstado("Imagen exportada exitosamente");
        }
    }

    @Override
    public void cargarEjemplo() {
        grafo.cargarEjemplo();
        panelGrafo.limpiarResaltado();
        panelGrafo.repaint();
        actualizarEstado("Ejemplo cargado - " + grafo.getContadorNodos() + " nodos");
        DialogosModernos.mostrarMensaje(this, "Grafo de ejemplo cargado con " + grafo.getContadorNodos() + " nodos.",
                "Ejemplo Cargado", DialogosModernos.TipoMensaje.EXITO);
    }

    @Override
    public void deshacer() {
        if (gestorHistorial.deshacer(grafo)) {
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Deshacer - " + gestorHistorial.getTamanoDeshacer() + " acciones disponibles");
            DialogosModernos.mostrarMensaje(this, "Última acción deshecha", "Deshacer",
                    DialogosModernos.TipoMensaje.EXITO);
        } else {
            DialogosModernos.mostrarMensaje(this, "No hay acciones para deshacer", "Deshacer",
                    DialogosModernos.TipoMensaje.ADVERTENCIA);
        }
    }

    @Override
    public void rehacer() {
        if (gestorHistorial.rehacer(grafo)) {
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Rehacer - " + gestorHistorial.getTamanoRehacer() + " acciones disponibles");
            DialogosModernos.mostrarMensaje(this, "Acción rehecha", "Rehacer", DialogosModernos.TipoMensaje.EXITO);
        } else {
            DialogosModernos.mostrarMensaje(this, "No hay acciones para rehacer", "Rehacer",
                    DialogosModernos.TipoMensaje.ADVERTENCIA);
        }
    }

    @Override
    public void crearArista() {
        if (grafo.getContadorNodos() < 2) {
            JOptionPane.showMessageDialog(this, "Debe haber al menos 2 nodos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(this, "Ingrese el nodo origen:",
                    grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(this, "Ingrese el nodo destino:",
                    grafo.getContadorNodos());
            if (!ValidadorEntrada.validarNodosDiferentes(this, origen, destino))
                return;
            int peso = ValidadorEntrada.solicitarPeso(this, "Ingrese el peso:");
            gestorHistorial.guardarEstado(grafo);
            grafo.agregarArista(origen, destino, peso);
            panelGrafo.repaint();
            actualizarEstado("Arista creada: " + (origen + 1) + " → " + (destino + 1));
        } catch (IllegalStateException ignored) {
        }
    }

    @Override
    public void eliminarArista() {
        if (grafo.getContadorNodos() < 2) {
            JOptionPane.showMessageDialog(this, "No hay aristas para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(this, "Ingrese el nodo origen:",
                    grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(this, "Ingrese el nodo destino:",
                    grafo.getContadorNodos());
            gestorHistorial.guardarEstado(grafo);
            grafo.eliminarArista(origen, destino);
            panelGrafo.repaint();
            actualizarEstado("Arista eliminada");
        } catch (IllegalStateException ignored) {
        }
    }

    @Override
    public void eliminarTodasAristas() {
        if (DialogosModernos.mostrarDialogoConfirmacion(this, "¿Eliminar todas las aristas?", "Confirmar")) {
            gestorHistorial.guardarEstado(grafo);
            grafo.limpiarAristas();
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Todas las aristas eliminadas");
        }
    }

    @Override
    public void eliminarNodo() {
        if (grafo.getContadorNodos() == 0) {
            JOptionPane.showMessageDialog(this, "No hay nodos para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int nodo = ValidadorEntrada.solicitarNodoOrigen(this, "Ingrese el nodo a eliminar:",
                    grafo.getContadorNodos());
            gestorHistorial.guardarEstado(grafo);
            grafo.eliminarNodo(nodo);
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Nodo " + (nodo + 1) + " eliminado");
        } catch (IllegalStateException ignored) {
        }
    }

    @Override
    public void ejecutarDijkstra() {
        controladorAlgoritmos.ejecutarDijkstra();
    }

    @Override
    public void ejecutarBFS() {
        controladorAlgoritmos.ejecutarBFS();
    }

    @Override
    public void ejecutarDFS() {
        controladorAlgoritmos.ejecutarDFS();
    }

    @Override
    public void ejecutarAStar() {
        controladorAlgoritmos.ejecutarAStar();
    }

    @Override
    public void ejecutarPrim() {
        controladorAlgoritmos.ejecutarPrim();
    }

    @Override
    public void toggleModoOscuro() {
        gestorTemas.toggleModoOscuro(this, panelGrafo);
        actualizarEstado(gestorTemas.getMensajeModoActual());
    }

    @Override
    public void zoomIn() {
        if (gestorZoom.zoomIn()) {
            aplicarZoom();
            actualizarEstado(gestorZoom.getMensajeZoom());
        }
    }

    @Override
    public void zoomOut() {
        if (gestorZoom.zoomOut()) {
            aplicarZoom();
            actualizarEstado(gestorZoom.getMensajeZoom());
        }
    }

    @Override
    public void zoomReset() {
        gestorZoom.zoomReset();
        aplicarZoom();
        actualizarEstado(gestorZoom.getMensajeZoom());
    }

    private void aplicarZoom() {
        double factor = gestorZoom.getZoomFactor();
        panelGrafo.setEscalaZoom(factor);
        panelGrafo.setPreferredSize(new Dimension((int) (3000 * factor), (int) (3000 * factor)));
        panelGrafo.revalidate();
        panelGrafo.repaint();
        scrollPane.revalidate();
    }

    @Override
    public void mostrarMatrizAdyacencia() {
        if (grafo.getContadorNodos() == 0) {
            JOptionPane.showMessageDialog(this, "No hay nodos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new VentanaMatriz(grafo, true).setVisible(true);
    }

    @Override
    public void mostrarMatrizPesos() {
        if (grafo.getContadorNodos() == 0) {
            JOptionPane.showMessageDialog(this, "No hay nodos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new VentanaMatriz(grafo, false).setVisible(true);
    }

    @Override
    public void mostrarAcercaDe() {
        new DialogoAcercaDe(this).setVisible(true);
    }
}
