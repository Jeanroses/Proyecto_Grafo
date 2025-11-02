package com.grafo.ui;

import com.grafo.algoritmos.*;
import com.grafo.model.*;
import com.grafo.util.*;
import com.grafo.controladores.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    private final Grafo grafo;
    private final PanelGrafo panelGrafo;
    private final GestorHistorial gestorHistorial;
    private final ControladorMouse controladorMouse;
    private final ControladorAlgoritmos controladorAlgoritmos;
    private final GestorZoom gestorZoom;
    private final GestorTemas gestorTemas;

    private JTextField txtDistanciaTotal;
    private JLabel lblEstado;
    private JScrollPane scrollPane;
    private JTextArea txtResultadoAlgoritmo;

    private Integer nodoSeleccionado1 = null;
    private Integer nodoSeleccionado2 = null;

    public VentanaPrincipal() {
        ModernUI.applyGlobalFont("Segoe UI", 13);

        this.grafo = new Grafo();
        this.panelGrafo = new PanelGrafo(grafo);
        this.gestorHistorial = new GestorHistorial();
        this.gestorZoom = new GestorZoom();
        this.gestorTemas = new GestorTemas();

        this.controladorMouse = new ControladorMouse(grafo, panelGrafo, gestorHistorial, this::actualizarEstado);
        this.controladorAlgoritmos = new ControladorAlgoritmos(
            grafo, panelGrafo, this,
            text -> txtDistanciaTotal.setText(text),
            this::actualizarEstado,
            this::mostrarResultadoEnPanel
        );

        inicializarUI();
        configurarEventos();
        configurarAtajos();
    }

    private void inicializarUI() {
        setTitle("Sistema de grafos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        configurarIconoAplicacion();

        scrollPane = new JScrollPane(panelGrafo);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        add(crearPanelControles(), BorderLayout.WEST);
        add(crearPanelInformacion(), BorderLayout.SOUTH);
        setJMenuBar(crearBarraMenu());
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ModernUI.PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 223, 226)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        panel.setPreferredSize(new Dimension(260, 0));

        JLabel titulo = new JLabel("PANEL DE CONTROL");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(ModernUI.PRIMARY_DARK);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(16));

        JPanel cardAcciones = ModernUI.cardPanel();
        cardAcciones.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tituloAcciones = new JLabel("ACCIONES RAPIDAS");
        tituloAcciones.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tituloAcciones.setForeground(ModernUI.TEXT_DARK);
        cardAcciones.add(tituloAcciones);
        cardAcciones.add(Box.createVerticalStrut(10));

        JButton btnLimpiar = crearBotonRedondeado("Limpiar Todo", ModernUI.DANGER);
        btnLimpiar.addActionListener(_ -> limpiarTodo());

        JButton btnRepintar = crearBotonRedondeado("Repintar", ModernUI.PRIMARY);
        btnRepintar.addActionListener(_ -> {
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Grafo repintado");
        });

        JButton btnEjemplo = crearBotonRedondeado("Cargar Ejemplo", ModernUI.PURPLE);
        btnEjemplo.addActionListener(_ -> cargarEjemplo());

        cardAcciones.add(btnLimpiar);
        cardAcciones.add(Box.createVerticalStrut(8));
        cardAcciones.add(btnRepintar);
        cardAcciones.add(Box.createVerticalStrut(8));
        cardAcciones.add(btnEjemplo);

        JPanel cardInstrucciones = ModernUI.cardPanel();
        cardInstrucciones.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tituloInstrucciones = new JLabel("INSTRUCCIONES");
        tituloInstrucciones.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tituloInstrucciones.setForeground(ModernUI.TEXT_DARK);
        cardInstrucciones.add(tituloInstrucciones);
        cardInstrucciones.add(Box.createVerticalStrut(8));

        cardInstrucciones.add(crearItemInstruccion("Click Izq", "Crear nodo"));
        cardInstrucciones.add(crearItemInstruccion("Click Der", "Unir nodos"));
        cardInstrucciones.add(crearItemInstruccion("Ctrl + Click", "Mover nodo"));
        cardInstrucciones.add(crearItemInstruccion("Ctrl + Scroll", "Zoom"));

        JPanel cardAtajos = ModernUI.cardPanel();
        cardAtajos.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel tituloAtajos = new JLabel("ATAJOS UTILES");
        tituloAtajos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tituloAtajos.setForeground(ModernUI.TEXT_DARK);
        cardAtajos.add(tituloAtajos);
        cardAtajos.add(Box.createVerticalStrut(8));

        cardAtajos.add(crearItemInstruccion("Ctrl+Z", "Deshacer"));
        cardAtajos.add(crearItemInstruccion("Ctrl+Y", "Rehacer"));
        cardAtajos.add(crearItemInstruccion("Ctrl+S", "Guardar"));
        cardAtajos.add(crearItemInstruccion("Ctrl+O", "Abrir"));
        cardAtajos.add(crearItemInstruccion("Ctrl+E", "Exportar"));
        cardAtajos.add(crearItemInstruccion("F2", "Modo Oscuro"));

        panel.add(cardAcciones);
        panel.add(Box.createVerticalStrut(12));
        panel.add(cardInstrucciones);
        panel.add(Box.createVerticalStrut(12));
        panel.add(cardAtajos);

        return panel;
    }

    private JButton crearBotonRedondeado(String text, Color base) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(base.darker().darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(base.darker());
                } else {
                    g2.setColor(base);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(200, 36));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        return btn;
    }

    private JPanel crearItemInstruccion(String tecla, String descripcion) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTecla = new JLabel(tecla);
        lblTecla.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTecla.setForeground(ModernUI.PRIMARY);
        lblTecla.setOpaque(true);
        lblTecla.setBackground(new Color(230, 240, 255));
        lblTecla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernUI.PRIMARY, 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));

        JLabel lblFlecha = new JLabel("→");
        lblFlecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFlecha.setForeground(ModernUI.TEXT_DARK);

        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(ModernUI.TEXT_DARK);

        panel.add(lblTecla);
        panel.add(lblFlecha);
        panel.add(lblDesc);

        return panel;
    }

    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ModernUI.PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 223, 226)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelEstado.setBackground(ModernUI.PANEL_BG);

        JLabel iconoEstado = new JLabel("[i]");
        iconoEstado.setFont(new Font("Segoe UI", Font.BOLD, 13));
        iconoEstado.setForeground(ModernUI.PRIMARY);

        lblEstado = new JLabel("Listo");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblEstado.setForeground(ModernUI.TEXT_DARK);

        panelEstado.add(iconoEstado);
        panelEstado.add(lblEstado);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(ModernUI.PANEL_BG);

        txtResultadoAlgoritmo = new JTextArea(1, 50);
        txtResultadoAlgoritmo.setEditable(false);
        txtResultadoAlgoritmo.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResultadoAlgoritmo.setBackground(new Color(248, 250, 252));
        txtResultadoAlgoritmo.setForeground(ModernUI.TEXT_DARK);
        txtResultadoAlgoritmo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        txtResultadoAlgoritmo.setLineWrap(false);
        txtResultadoAlgoritmo.setText("Ejecute un algoritmo para ver resultados aquí");

        panelCentro.add(txtResultadoAlgoritmo, BorderLayout.CENTER);

        JPanel panelDistancia = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDistancia.setBackground(ModernUI.PANEL_BG);

        JLabel lblDistanciaLabel = new JLabel("Distancia:");
        lblDistanciaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDistanciaLabel.setForeground(ModernUI.TEXT_DARK);

        txtDistanciaTotal = new JTextField(15);
        txtDistanciaTotal.setEditable(false);
        txtDistanciaTotal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDistanciaTotal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        panelDistancia.add(lblDistanciaLabel);
        panelDistancia.add(txtDistanciaTotal);

        panel.add(panelEstado, BorderLayout.WEST);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelDistancia, BorderLayout.EAST);

        return panel;
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(ModernUI.PANEL_BG);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 223, 226)));

        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemNuevo = new JMenuItem("Nuevo Proyecto");
        itemNuevo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        itemNuevo.addActionListener(_ -> limpiarTodo());

        JMenuItem itemAbrir = new JMenuItem("Abrir Grafo");
        itemAbrir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        itemAbrir.addActionListener(_ -> abrirGrafo());

        JMenuItem itemGuardar = new JMenuItem("Guardar Grafo");
        itemGuardar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemGuardar.addActionListener(_ -> guardarGrafo());

        JMenuItem itemExportar = new JMenuItem("Exportar como PNG");
        itemExportar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemExportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        itemExportar.addActionListener(_ -> exportarPNG());

        JMenuItem itemEjemplo = new JMenuItem("Cargar Ejemplo");
        itemEjemplo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemEjemplo.addActionListener(_ -> cargarEjemplo());

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        itemSalir.addActionListener(_ -> System.exit(0));

        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemExportar);
        menuArchivo.add(itemEjemplo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        JMenu menuEditar = new JMenu("Editar");

        JMenuItem itemDeshacer = new JMenuItem("Deshacer");
        itemDeshacer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        itemDeshacer.addActionListener(_ -> deshacer());

        JMenuItem itemRehacer = new JMenuItem("Rehacer");
        itemRehacer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemRehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        itemRehacer.addActionListener(_ -> rehacer());

        menuEditar.add(itemDeshacer);
        menuEditar.add(itemRehacer);

        JMenu menuAristas = new JMenu("Aristas");

        JMenuItem itemNuevaArista = new JMenuItem("Nueva Arista");
        itemNuevaArista.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemNuevaArista.addActionListener(_ -> crearArista());

        JMenuItem itemEliminarArista = new JMenuItem("Eliminar Arista");
        itemEliminarArista.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemEliminarArista.addActionListener(_ -> eliminarArista());

        JMenuItem itemEliminarTodasAristas = new JMenuItem("Eliminar Todas las Aristas");
        itemEliminarTodasAristas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemEliminarTodasAristas.addActionListener(_ -> eliminarTodasAristas());

        menuAristas.add(itemNuevaArista);
        menuAristas.add(itemEliminarArista);
        menuAristas.addSeparator();
        menuAristas.add(itemEliminarTodasAristas);

        JMenu menuNodos = new JMenu("Nodos");

        JMenuItem itemEliminarNodo = new JMenuItem("Eliminar Nodo");
        itemEliminarNodo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemEliminarNodo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK));
        itemEliminarNodo.addActionListener(_ -> eliminarNodo());

        menuNodos.add(itemEliminarNodo);

        JMenu menuAlgoritmos = new JMenu("Algoritmos");

        JMenuItem itemDijkstra = new JMenuItem("Dijkstra - Camino Mas Corto");
        itemDijkstra.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemDijkstra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        itemDijkstra.addActionListener(_ -> ejecutarDijkstra());

        JMenuItem itemBFS = new JMenuItem("BFS - Busqueda en Anchura");
        itemBFS.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemBFS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
        itemBFS.addActionListener(_ -> ejecutarBFS());

        JMenuItem itemDFS = new JMenuItem("DFS - Busqueda en Profundidad");
        itemDFS.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemDFS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        itemDFS.addActionListener(_ -> ejecutarDFS());

        JMenuItem itemPrim = new JMenuItem("Prim - Arbol Recubrimiento Minimo");
        itemPrim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemPrim.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        itemPrim.addActionListener(_ -> ejecutarPrim());

        menuAlgoritmos.add(itemDijkstra);
        menuAlgoritmos.add(itemBFS);
        menuAlgoritmos.add(itemDFS);
        menuAlgoritmos.addSeparator();
        menuAlgoritmos.add(itemPrim);

        JMenu menuVer = new JMenu("Ver");

        JMenuItem itemModoOscuro = new JMenuItem("Modo Oscuro");
        itemModoOscuro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemModoOscuro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        itemModoOscuro.addActionListener(_ -> toggleModoOscuro());

        JMenuItem itemZoomIn = new JMenuItem("Zoom In");
        itemZoomIn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK));
        itemZoomIn.addActionListener(_ -> zoomIn());

        JMenuItem itemZoomOut = new JMenuItem("Zoom Out");
        itemZoomOut.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK));
        itemZoomOut.addActionListener(_ -> zoomOut());

        JMenuItem itemZoomReset = new JMenuItem("Zoom 100%");
        itemZoomReset.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemZoomReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
        itemZoomReset.addActionListener(_ -> zoomReset());

        JMenuItem itemMatrizAdy = new JMenuItem("Matriz de Adyacencia");
        itemMatrizAdy.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemMatrizAdy.addActionListener(_ -> mostrarMatrizAdyacencia());

        JMenuItem itemMatrizPesos = new JMenuItem("Matriz de Pesos");
        itemMatrizPesos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemMatrizPesos.addActionListener(_ -> mostrarMatrizPesos());

        menuVer.add(itemModoOscuro);
        menuVer.addSeparator();
        menuVer.add(itemZoomIn);
        menuVer.add(itemZoomOut);
        menuVer.add(itemZoomReset);
        menuVer.addSeparator();
        menuVer.add(itemMatrizAdy);
        menuVer.add(itemMatrizPesos);

        JMenu menuAyuda = new JMenu("Ayuda");

        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemAcercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        itemAcercaDe.addActionListener(_ -> mostrarAcercaDe());

        menuAyuda.add(itemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuEditar);
        menuBar.add(menuAristas);
        menuBar.add(menuNodos);
        menuBar.add(menuAlgoritmos);
        menuBar.add(menuVer);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    private void configurarEventos() {
        controladorMouse.setCallbackCrearNodo(point -> manejarCrearNodo(point.x, point.y));
        controladorMouse.setCallbackClickDerecho(point -> manejarClickDerecho(point.x, point.y));
        controladorMouse.configurarEventos();

        panelGrafo.addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                if (e.getWheelRotation() < 0) {
                    zoomIn();
                } else {
                    zoomOut();
                }
            }
        });
    }

    private void configurarAtajos() {
    }

    private void configurarIconoAplicacion() {
        try {
            Image icono = GestorImagenes.cargarIconoAplicacionEscalado(64, 64);
            if (icono != null) {
                setIconImage(icono);
            } else {
                System.err.println("No se pudo establecer el icono de la aplicación");
            }
        } catch (Exception e) {
            System.err.println("Error al configurar el icono de la aplicación: " + e.getMessage());
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

        if (nodoOpt.isEmpty()) {
            return;
        }

        int idNodo = nodoOpt.get().getId();

        if (nodoSeleccionado1 == null) {
            nodoSeleccionado1 = idNodo;
            panelGrafo.setNodoSeleccionado1(idNodo);
            panelGrafo.repaint();
            actualizarEstado("Nodo " + (idNodo + 1) + " seleccionado. Seleccione el segundo nodo");
        } else if (nodoSeleccionado2 == null) {
            if (idNodo == nodoSeleccionado1) {
                nodoSeleccionado1 = null;
                panelGrafo.setNodoSeleccionado1(null);
                panelGrafo.repaint();
                actualizarEstado("Selección cancelada");
                return;
            }

            nodoSeleccionado2 = idNodo;

            try {
                int peso = ValidadorEntrada.solicitarPeso(this, "Ingrese el peso de la arista:");
                gestorHistorial.guardarEstado(grafo);
                grafo.agregarArista(nodoSeleccionado1, nodoSeleccionado2, peso);

                actualizarEstado("Arista creada: " + (nodoSeleccionado1 + 1) + " → " + (nodoSeleccionado2 + 1) + " (peso: " + peso + ")");
                nodoSeleccionado1 = null;
                nodoSeleccionado2 = null;
                panelGrafo.setNodoSeleccionado1(null);
                panelGrafo.repaint();
            } catch (IllegalStateException ex) {
                nodoSeleccionado1 = null;
                nodoSeleccionado2 = null;
                panelGrafo.setNodoSeleccionado1(null);
                panelGrafo.repaint();
                actualizarEstado("Operación cancelada");
            }
        }
    }

    private void crearArista() {
        if (grafo.getContadorNodos() < 2) {
            JOptionPane.showMessageDialog(this,
                "Debe haber al menos 2 nodos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(
                this, "Ingrese el nodo origen:", grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(
                this, "Ingrese el nodo destino:", grafo.getContadorNodos());

            if (!ValidadorEntrada.validarNodosDiferentes(this, origen, destino)) {
                return;
            }

            int peso = ValidadorEntrada.solicitarPeso(this, "Ingrese el peso:");
            gestorHistorial.guardarEstado(grafo);
            grafo.agregarArista(origen, destino, peso);
            panelGrafo.repaint();
            actualizarEstado("Arista creada: " + (origen + 1) + " → " + (destino + 1));
        } catch (IllegalStateException ignored) {
        }
    }

    private void eliminarArista() {
        if (grafo.getContadorNodos() < 2) {
            JOptionPane.showMessageDialog(this,
                "No hay aristas para eliminar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int origen = ValidadorEntrada.solicitarNodoOrigen(
                this, "Ingrese el nodo origen:", grafo.getContadorNodos());
            int destino = ValidadorEntrada.solicitarNodoOrigen(
                this, "Ingrese el nodo destino:", grafo.getContadorNodos());

            gestorHistorial.guardarEstado(grafo);
            grafo.eliminarArista(origen, destino);
            panelGrafo.repaint();
            actualizarEstado("Arista eliminada");
        } catch (IllegalStateException ignored) {
        }
    }

    private void eliminarTodasAristas() {
        boolean confirmar = DialogosModernos.mostrarDialogoConfirmacion(this,
            "¿Está seguro de eliminar todas las aristas?",
            "Confirmar Eliminación");

        if (confirmar) {
            gestorHistorial.guardarEstado(grafo);
            grafo.limpiarAristas();
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Todas las aristas eliminadas");
        }
    }

    private void eliminarNodo() {
        if (grafo.getContadorNodos() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay nodos para eliminar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int nodo = ValidadorEntrada.solicitarNodoOrigen(
                this, "Ingrese el nodo a eliminar:", grafo.getContadorNodos());

            gestorHistorial.guardarEstado(grafo);
            grafo.eliminarNodo(nodo);
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Nodo " + (nodo + 1) + " eliminado");
        } catch (IllegalStateException ignored) {
        }
    }

    private void ejecutarDijkstra() {
        controladorAlgoritmos.ejecutarDijkstra();
    }

    private void ejecutarBFS() {
        controladorAlgoritmos.ejecutarBFS();
    }

    private void ejecutarDFS() {
        controladorAlgoritmos.ejecutarDFS();
    }

    private void ejecutarPrim() {
        controladorAlgoritmos.ejecutarPrim();
    }

    private void mostrarMatrizAdyacencia() {
        if (grafo.getContadorNodos() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay nodos para mostrar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        VentanaMatriz ventana = new VentanaMatriz(grafo, true);
        ventana.setVisible(true);
    }

    private void mostrarMatrizPesos() {
        if (grafo.getContadorNodos() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay nodos para mostrar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        VentanaMatriz ventana = new VentanaMatriz(grafo, false);
        ventana.setVisible(true);
    }

    private void limpiarTodo() {
        boolean confirmar = DialogosModernos.mostrarDialogoConfirmacion(this,
            "¿Está seguro de limpiar todo el grafo?\n\nSe perderán todos los nodos y aristas.",
            "Confirmar Limpieza");

        if (confirmar) {
            grafo.limpiar();
            gestorHistorial.limpiar();
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            txtDistanciaTotal.setText("");
            nodoSeleccionado1 = null;
            nodoSeleccionado2 = null;
            actualizarEstado("Grafo limpiado");
        }
    }

    private void cargarEjemplo() {
        grafo.cargarEjemplo();
        panelGrafo.limpiarResaltado();
        panelGrafo.repaint();
        actualizarEstado("Ejemplo cargado - " + grafo.getContadorNodos() + " nodos");

        DialogosModernos.mostrarMensaje(this,
            "Grafo de ejemplo cargado con " + grafo.getContadorNodos() + " nodos.\n\n" +
            "Puede probar los algoritmos desde el menú 'Algoritmos'.",
            "Ejemplo Cargado",
            DialogosModernos.TipoMensaje.EXITO);
    }

    private void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    private void mostrarResultadoEnPanel(String resultado) {
        txtResultadoAlgoritmo.setText(resultado);
    }

    private void deshacer() {
        if (gestorHistorial.deshacer(grafo)) {
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Deshacer - " + gestorHistorial.getTamanoDeshacer() + " acciones disponibles");
            DialogosModernos.mostrarMensaje(this,
                    "Última acción deshecha exitosamente",
                    "Deshacer",
                    DialogosModernos.TipoMensaje.EXITO);
        } else {
            DialogosModernos.mostrarMensaje(this,
                    "No hay acciones para deshacer",
                    "Deshacer",
                    DialogosModernos.TipoMensaje.ADVERTENCIA);
        }
    }

    private void rehacer() {
        if (gestorHistorial.rehacer(grafo)) {
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Rehacer - " + gestorHistorial.getTamanoRehacer() + " acciones disponibles");
            DialogosModernos.mostrarMensaje(this,
                    "Acción rehecha exitosamente",
                    "Rehacer",
                    DialogosModernos.TipoMensaje.EXITO);
        } else {
            DialogosModernos.mostrarMensaje(this,
                    "No hay acciones para rehacer",
                    "Rehacer",
                    DialogosModernos.TipoMensaje.ADVERTENCIA);
        }
    }

    private void guardarGrafo() {
        if (GestorArchivos.guardarGrafo(this, grafo)) {
            actualizarEstado("Grafo guardado exitosamente");
        }
    }

    private void abrirGrafo() {
        if (GestorArchivos.abrirGrafo(this, grafo)) {
            gestorHistorial.limpiar();
            panelGrafo.limpiarResaltado();
            panelGrafo.repaint();
            actualizarEstado("Grafo cargado con " + grafo.getContadorNodos() + " nodos");
        }
    }

    private void exportarPNG() {
        if (GestorImagenes.exportarPNG(this, panelGrafo)) {
            actualizarEstado("Imagen exportada exitosamente");
        }
    }

    private void toggleModoOscuro() {
        gestorTemas.toggleModoOscuro(this, panelGrafo);
        actualizarEstado(gestorTemas.getMensajeModoActual());
    }

    private void zoomIn() {
        if (gestorZoom.zoomIn()) {
            aplicarZoom();
            actualizarEstado(gestorZoom.getMensajeZoom());
        }
    }

    private void zoomOut() {
        if (gestorZoom.zoomOut()) {
            aplicarZoom();
            actualizarEstado(gestorZoom.getMensajeZoom());
        }
    }

    private void zoomReset() {
        gestorZoom.zoomReset();
        aplicarZoom();
        actualizarEstado(gestorZoom.getMensajeZoom());
    }

    private void aplicarZoom() {
        double factor = gestorZoom.getZoomFactor();
        panelGrafo.setEscalaZoom(factor);

        int nuevoAncho = (int) (3000 * factor);
        int nuevoAlto = (int) (3000 * factor);
        panelGrafo.setPreferredSize(new Dimension(nuevoAncho, nuevoAlto));

        panelGrafo.revalidate();
        panelGrafo.repaint();
        scrollPane.revalidate();
    }

    private void mostrarAcercaDe() {

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel(InfoAplicacion.NOMBRE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(37, 99, 235));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblVersion = new JLabel("Versión " + InfoAplicacion.VERSION + " - " + InfoAplicacion.FECHA_VERSION);
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblVersion.setForeground(new Color(107, 114, 128));
        lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelAutor = crearPanelInfo("Autor", InfoAplicacion.AUTOR);
        JPanel panelCurso = crearPanelInfo("Curso", InfoAplicacion.CURSO);
        JPanel panelGitHub = crearPanelInfo("GitHub", "github.com/Jeanroses");


        JLabel lblAlgoritmos = new JLabel("Algoritmos Implementados");
        lblAlgoritmos.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblAlgoritmos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAlgo1 = new JLabel("Dijkstra - Camino más corto");
        lblAlgo1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblAlgo1.setForeground(new Color(107, 114, 128));
        lblAlgo1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAlgo2 = new JLabel("BFS - Búsqueda en anchura");
        lblAlgo2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblAlgo2.setForeground(new Color(107, 114, 128));
        lblAlgo2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAlgo3 = new JLabel("DFS - Búsqueda en profundidad");
        lblAlgo3.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblAlgo3.setForeground(new Color(107, 114, 128));
        lblAlgo3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAlgo4 = new JLabel("Prim - Árbol de recubrimiento mínimo");
        lblAlgo4.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblAlgo4.setForeground(new Color(107, 114, 128));
        lblAlgo4.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLicencia = new JLabel(InfoAplicacion.LICENCIA);
        lblLicencia.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        lblLicencia.setForeground(new Color(156, 163, 175));
        lblLicencia.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCopyright = new JLabel(InfoAplicacion.COPYRIGHT);
        lblCopyright.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        lblCopyright.setForeground(new Color(156, 163, 175));
        lblCopyright.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(3));
        panelPrincipal.add(lblVersion);
        panelPrincipal.add(Box.createVerticalStrut(12));
        panelPrincipal.add(crearSeparador());
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(panelAutor);
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(panelCurso);
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(panelGitHub);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearSeparador());
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(lblAlgoritmos);
        panelPrincipal.add(Box.createVerticalStrut(5));
        panelPrincipal.add(lblAlgo1);
        panelPrincipal.add(Box.createVerticalStrut(2));
        panelPrincipal.add(lblAlgo2);
        panelPrincipal.add(Box.createVerticalStrut(2));
        panelPrincipal.add(lblAlgo3);
        panelPrincipal.add(Box.createVerticalStrut(2));
        panelPrincipal.add(lblAlgo4);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearSeparador());
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(lblLicencia);
        panelPrincipal.add(Box.createVerticalStrut(3));
        panelPrincipal.add(lblCopyright);

        JDialog dialog = new JDialog(this, "Acerca de", true);
        dialog.setContentPane(panelPrincipal);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JPanel crearPanelInfo(String etiqueta, String texto) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblEtiqueta = new JLabel(etiqueta + ":");
        lblEtiqueta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEtiqueta.setForeground(new Color(55, 65, 81));

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTexto.setForeground(new Color(55, 65, 81));

        panel.add(lblEtiqueta);
        panel.add(lblTexto);

        return panel;
    }

    private JSeparator crearSeparador() {
        JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
        separador.setMaximumSize(new Dimension(300, 1));
        separador.setForeground(new Color(229, 231, 235));
        return separador;
    }
}
