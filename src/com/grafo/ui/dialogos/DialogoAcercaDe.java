package com.grafo.ui.dialogos;

import com.grafo.util.InfoAplicacion;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo "Acerca de" con información de la aplicación
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class DialogoAcercaDe extends JDialog {

    public DialogoAcercaDe(Frame parent) {
        super(parent, "Acerca de", true);
        setContentPane(crearPanelContenido());
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private JPanel crearPanelContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panel.add(crearLabel(InfoAplicacion.NOMBRE, new Font("Segoe UI", Font.BOLD, 20), new Color(37, 99, 235)));
        panel.add(Box.createVerticalStrut(3));
        panel.add(crearLabel("Versión " + InfoAplicacion.VERSION + " - " + InfoAplicacion.FECHA_VERSION,
                new Font("Segoe UI", Font.PLAIN, 11), new Color(107, 114, 128)));
        panel.add(Box.createVerticalStrut(12));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearPanelInfo("Autor", InfoAplicacion.AUTOR));
        panel.add(Box.createVerticalStrut(8));
        panel.add(crearPanelInfo("Curso", InfoAplicacion.CURSO));
        panel.add(Box.createVerticalStrut(8));
        panel.add(crearPanelInfo("GitHub", "github.com/Jeanroses"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearLabel("Algoritmos Implementados", new Font("Segoe UI", Font.BOLD, 11), Color.BLACK));
        panel.add(Box.createVerticalStrut(5));

        String[] algoritmos = { "Dijkstra", "BFS", "DFS", "A*", "Prim" };
        for (String algo : algoritmos) {
            panel.add(crearLabel(algo, new Font("Segoe UI", Font.PLAIN, 10), new Color(107, 114, 128)));
            panel.add(Box.createVerticalStrut(2));
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(8));
        panel.add(crearLabel(InfoAplicacion.LICENCIA, new Font("Segoe UI", Font.PLAIN, 9), new Color(156, 163, 175)));
        panel.add(Box.createVerticalStrut(3));
        panel.add(crearLabel(InfoAplicacion.COPYRIGHT, new Font("Segoe UI", Font.PLAIN, 9), new Color(156, 163, 175)));

        return panel;
    }

    private JLabel crearLabel(String texto, Font font, Color color) {
        JLabel label = new JLabel(texto);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
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
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }
}
