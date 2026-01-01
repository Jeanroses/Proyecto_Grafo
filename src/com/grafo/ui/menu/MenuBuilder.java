package com.grafo.ui.menu;

import com.grafo.util.ModernUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Builder para construir la barra de menú de la aplicación
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class MenuBuilder {

        private final JMenuBar menuBar;
        private final AccionesMenu acciones;

        public MenuBuilder(AccionesMenu acciones) {
                this.acciones = acciones;
                this.menuBar = new JMenuBar();
                menuBar.setBackground(ModernUI.PANEL_BG);
                menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 223, 226)));
        }

        public JMenuBar construir() {
                menuBar.add(crearMenuArchivo());
                menuBar.add(crearMenuEditar());
                menuBar.add(crearMenuAristas());
                menuBar.add(crearMenuNodos());
                menuBar.add(crearMenuAlgoritmos());
                menuBar.add(crearMenuVer());
                menuBar.add(crearMenuAyuda());
                return menuBar;
        }

        private JMenu crearMenuArchivo() {
                JMenu menu = new JMenu("Archivo");
                menu.add(crearItem("Nuevo Proyecto", KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.limpiarTodo()));
                menu.add(crearItem("Abrir Grafo", KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.abrirGrafo()));
                menu.add(crearItem("Guardar Grafo", KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.guardarGrafo()));
                menu.addSeparator();
                menu.add(crearItem("Exportar como PNG", KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.exportarPNG()));
                menu.add(crearItem("Cargar Ejemplo", 0, 0, e -> acciones.cargarEjemplo()));
                menu.addSeparator();
                menu.add(crearItem("Salir", KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK, e -> System.exit(0)));
                return menu;
        }

        private JMenu crearMenuEditar() {
                JMenu menu = new JMenu("Editar");
                menu.add(crearItem("Deshacer", KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK, e -> acciones.deshacer()));
                menu.add(crearItem("Rehacer", KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK, e -> acciones.rehacer()));
                return menu;
        }

        private JMenu crearMenuAristas() {
                JMenu menu = new JMenu("Aristas");
                menu.add(crearItem("Nueva Arista", 0, 0, e -> acciones.crearArista()));
                menu.add(crearItem("Eliminar Arista", 0, 0, e -> acciones.eliminarArista()));
                menu.addSeparator();
                menu.add(crearItem("Eliminar Todas las Aristas", 0, 0, e -> acciones.eliminarTodasAristas()));
                return menu;
        }

        private JMenu crearMenuNodos() {
                JMenu menu = new JMenu("Nodos");
                menu.add(crearItem("Eliminar Nodo", KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK,
                                e -> acciones.eliminarNodo()));
                return menu;
        }

        private JMenu crearMenuAlgoritmos() {
                JMenu menu = new JMenu("Algoritmos");
                menu.add(crearItem("Dijkstra - Camino Mas Corto", KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.ejecutarDijkstra()));
                menu.add(crearItem("BFS - Busqueda en Anchura", KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.ejecutarBFS()));
                menu.add(crearItem("DFS - Busqueda en Profundidad", KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.ejecutarDFS()));
                menu.add(crearItem("A* - Busqueda Heuristica", KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.ejecutarAStar()));
                menu.addSeparator();
                menu.add(crearItem("Prim - Arbol Recubrimiento Minimo", KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK,
                                e -> acciones.ejecutarPrim()));
                return menu;
        }

        private JMenu crearMenuVer() {
                JMenu menu = new JMenu("Ver");
                menu.add(crearItem("Modo Oscuro", KeyEvent.VK_F2, 0, e -> acciones.toggleModoOscuro()));
                menu.addSeparator();
                menu.add(crearItem("Zoom In", KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK, e -> acciones.zoomIn()));
                menu.add(crearItem("Zoom Out", KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK, e -> acciones.zoomOut()));
                menu.add(crearItem("Zoom 100%", KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK, e -> acciones.zoomReset()));
                menu.addSeparator();
                menu.add(crearItem("Matriz de Adyacencia", 0, 0, e -> acciones.mostrarMatrizAdyacencia()));
                menu.add(crearItem("Matriz de Pesos", 0, 0, e -> acciones.mostrarMatrizPesos()));
                return menu;
        }

        private JMenu crearMenuAyuda() {
                JMenu menu = new JMenu("Ayuda");
                menu.add(crearItem("Acerca de", KeyEvent.VK_F1, 0, e -> acciones.mostrarAcercaDe()));
                return menu;
        }

        private JMenuItem crearItem(String texto, int tecla, int modificador, ActionListener accion) {
                JMenuItem item = new JMenuItem(texto);
                item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (tecla != 0) {
                        item.setAccelerator(KeyStroke.getKeyStroke(tecla, modificador));
                }
                item.addActionListener(accion);
                return item;
        }
}
