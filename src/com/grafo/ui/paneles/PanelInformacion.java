package com.grafo.ui.paneles;

import com.grafo.util.ModernUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de información inferior con estado, resultado y distancia
 * 
 * @author Jean Paul Purihuaman De La Cruz
 */
public class PanelInformacion extends JPanel {

    private final JLabel lblEstado;
    private final JTextArea txtResultadoAlgoritmo;
    private final JTextField txtDistanciaTotal;

    public PanelInformacion() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ModernUI.PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 223, 226)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

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
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
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
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        panelDistancia.add(lblDistanciaLabel);
        panelDistancia.add(txtDistanciaTotal);

        add(panelEstado, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
        add(panelDistancia, BorderLayout.EAST);
    }

    public void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    public void actualizarResultado(String resultado) {
        txtResultadoAlgoritmo.setText(resultado);
    }

    public void actualizarDistancia(String distancia) {
        txtDistanciaTotal.setText(distancia);
    }

    public void limpiar() {
        txtDistanciaTotal.setText("");
        txtResultadoAlgoritmo.setText("Ejecute un algoritmo para ver resultados aquí");
        lblEstado.setText("Listo");
    }
}
