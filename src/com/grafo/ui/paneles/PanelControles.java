package com.grafo.ui.paneles;

import com.grafo.util.ModernUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de controles lateral izquierdo
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class PanelControles extends JPanel {

    public interface AccionesControles {
        void limpiarTodo();

        void repintar();

        void cargarEjemplo();
    }

    public PanelControles(AccionesControles acciones) {
        configurarPanel();
        agregarContenido(acciones);
    }

    private void configurarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ModernUI.PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 223, 226)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        setPreferredSize(new Dimension(260, 0));
    }

    private void agregarContenido(AccionesControles acciones) {
        add(crearTitulo());
        add(Box.createVerticalStrut(16));
        add(crearCardAcciones(acciones));
        add(Box.createVerticalStrut(12));
        add(crearCardInstrucciones());
        add(Box.createVerticalStrut(12));
        add(crearCardAtajos());
    }

    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("PANEL DE CONTROL");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(ModernUI.PRIMARY_DARK);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titulo;
    }

    private JPanel crearCardAcciones(AccionesControles acciones) {
        JPanel card = ModernUI.cardPanel();
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(crearLabelSeccion("ACCIONES RAPIDAS"));
        card.add(Box.createVerticalStrut(10));
        card.add(crearBoton("Limpiar Todo", ModernUI.DANGER, e -> acciones.limpiarTodo()));
        card.add(Box.createVerticalStrut(8));
        card.add(crearBoton("Repintar", ModernUI.PRIMARY, e -> acciones.repintar()));
        card.add(Box.createVerticalStrut(8));
        card.add(crearBoton("Cargar Ejemplo", ModernUI.PURPLE, e -> acciones.cargarEjemplo()));
        return card;
    }

    private JPanel crearCardInstrucciones() {
        JPanel card = ModernUI.cardPanel();
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(crearLabelSeccion("INSTRUCCIONES"));
        card.add(Box.createVerticalStrut(8));
        card.add(crearItemInstruccion("Click Izq", "Crear nodo"));
        card.add(crearItemInstruccion("Click Der", "Unir nodos"));
        card.add(crearItemInstruccion("Ctrl + Click", "Mover nodo"));
        card.add(crearItemInstruccion("Ctrl + Scroll", "Zoom"));
        return card;
    }

    private JPanel crearCardAtajos() {
        JPanel card = ModernUI.cardPanel();
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(crearLabelSeccion("ATAJOS UTILES"));
        card.add(Box.createVerticalStrut(8));
        card.add(crearItemInstruccion("Ctrl+Z", "Deshacer"));
        card.add(crearItemInstruccion("Ctrl+Y", "Rehacer"));
        card.add(crearItemInstruccion("Ctrl+S", "Guardar"));
        card.add(crearItemInstruccion("Ctrl+O", "Abrir"));
        card.add(crearItemInstruccion("Ctrl+E", "Exportar"));
        card.add(crearItemInstruccion("F2", "Modo Oscuro"));
        return card;
    }

    private JLabel crearLabelSeccion(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(ModernUI.TEXT_DARK);
        return label;
    }

    private JButton crearBoton(String texto, Color color, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(color.darker().darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.darker());
                } else {
                    g2.setColor(color);
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
        btn.addActionListener(accion);
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
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));

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
}
