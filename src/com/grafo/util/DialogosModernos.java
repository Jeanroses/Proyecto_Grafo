package com.grafo.util;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase de utilidad para mostrar cuadros de diálogo modernos y elegantes
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class DialogosModernos {

    private static final Color COLOR_PRIMARY = new Color(52, 152, 219);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_WARNING = new Color(241, 196, 15);
    private static final Color COLOR_DANGER = new Color(231, 76, 60);
    private static final Color COLOR_INFO = new Color(155, 89, 182);
    private static final Color COLOR_DARK = new Color(44, 62, 80);
    private static final Color COLOR_LIGHT = new Color(236, 240, 241);

    public static String mostrarDialogoEntrada(Component parent, String mensaje, String titulo) {
        Frame frameParent = null;
        if (parent != null) {
            Window window = SwingUtilities.getWindowAncestor(parent);
            if (window instanceof Frame) {
                frameParent = (Frame) window;
            }
        }
        JDialog dialog = new JDialog(frameParent, titulo, true);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.setSize(450, 220);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelMensaje = new JPanel(new BorderLayout(15, 0));
        panelMensaje.setBackground(Color.WHITE);

        JLabel iconoLabel = new JLabel("📝");
        iconoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        panelMensaje.add(iconoLabel, BorderLayout.WEST);

        JLabel lblMensaje = new JLabel("<html><div style='width: 250px'>" + mensaje + "</div></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensaje.setForeground(COLOR_DARK);
        panelMensaje.add(lblMensaje, BorderLayout.CENTER);

        panelPrincipal.add(panelMensaje, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        textField.setForeground(COLOR_DARK);
        textField.setCaretColor(COLOR_PRIMARY);
        textField.setBackground(Color.WHITE);
        textField.setSelectedTextColor(Color.WHITE);
        textField.setSelectionColor(COLOR_PRIMARY);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textField.setPreferredSize(new Dimension(0, 45));
        textField.setHorizontalAlignment(JTextField.CENTER);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_PRIMARY.brighter(), 2),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }
        });

        panelPrincipal.add(textField, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton btnCancelar = crearBotonModerno("Cancelar", COLOR_LIGHT, COLOR_DARK);
        JButton btnAceptar = crearBotonModerno("Aceptar", COLOR_PRIMARY, Color.WHITE);

        final String[] resultado = {null};

        btnAceptar.addActionListener(e -> {
            resultado[0] = textField.getText();
            dialog.dispose();
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        textField.addActionListener(e -> {
            resultado[0] = textField.getText();
            dialog.dispose();
        });

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        dialog.add(panelPrincipal);

        dialog.getRootPane().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        textField.requestFocusInWindow();
        dialog.setVisible(true);

        return resultado[0];
    }

    public static boolean mostrarDialogoConfirmacion(Component parent, String mensaje, String titulo) {
        Frame frameParent = null;
        if (parent != null) {
            Window window = SwingUtilities.getWindowAncestor(parent);
            if (window instanceof Frame) {
                frameParent = (Frame) window;
            }
        }
        JDialog dialog = new JDialog(frameParent, titulo, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(420, 180);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelMensaje = new JPanel(new BorderLayout(15, 0));
        panelMensaje.setBackground(Color.WHITE);

        JLabel iconoLabel = new JLabel("❓");
        iconoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        panelMensaje.add(iconoLabel, BorderLayout.WEST);

        JLabel lblMensaje = new JLabel("<html><div style='width: 280px'>" + mensaje + "</div></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensaje.setForeground(COLOR_DARK);
        panelMensaje.add(lblMensaje, BorderLayout.CENTER);

        panelPrincipal.add(panelMensaje, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton btnNo = crearBotonModerno("No", COLOR_LIGHT, COLOR_DARK);
        JButton btnSi = crearBotonModerno("Sí", COLOR_SUCCESS, Color.WHITE);

        final boolean[] resultado = {false};

        btnSi.addActionListener(e -> {
            resultado[0] = true;
            dialog.dispose();
        });

        btnNo.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnSi);
        panelBotones.add(btnNo);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        dialog.add(panelPrincipal);

        dialog.getRootPane().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        dialog.setVisible(true);

        return resultado[0];
    }

    public static void mostrarMensaje(Component parent, String mensaje, String titulo, TipoMensaje tipo) {
        Frame frameParent = null;
        if (parent != null) {
            Window window = SwingUtilities.getWindowAncestor(parent);
            if (window instanceof Frame) {
                frameParent = (Frame) window;
            }
        }
        JDialog dialog = new JDialog(frameParent, titulo, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelMensaje = new JPanel(new BorderLayout(15, 0));
        panelMensaje.setBackground(Color.WHITE);

        String icono = tipo.getIcono();
        Color color = tipo.getColor();

        JLabel iconoLabel = new JLabel(icono);
        iconoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        panelMensaje.add(iconoLabel, BorderLayout.WEST);

        JLabel lblMensaje = new JLabel("<html><div style='width: 260px'>" + mensaje + "</div></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensaje.setForeground(COLOR_DARK);
        panelMensaje.add(lblMensaje, BorderLayout.CENTER);

        panelPrincipal.add(panelMensaje, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton btnAceptar = crearBotonModerno("Aceptar", color, Color.WHITE);
        btnAceptar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnAceptar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        dialog.add(panelPrincipal);

        dialog.getRootPane().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        dialog.setVisible(true);
    }

    private static JButton crearBotonModerno(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(100, 35));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        Color colorOriginal = colorFondo;
        Color colorHover = colorFondo.darker();

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorOriginal);
            }
        });

        return boton;
    }

    public enum TipoMensaje {
        EXITO("✅", COLOR_SUCCESS),
        ERROR("❌", COLOR_DANGER),
        ADVERTENCIA("⚠️", COLOR_WARNING),
        INFORMACION("ℹ️", COLOR_INFO);

        private final String icono;
        private final Color color;

        TipoMensaje(String icono, Color color) {
            this.icono = icono;
            this.color = color;
        }

        public String getIcono() {
            return icono;
        }

        public Color getColor() {
            return color;
        }
    }
}
