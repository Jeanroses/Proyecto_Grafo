package com.grafo.ui;

import com.grafo.model.Grafo;
import com.grafo.util.GestorImagenes;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana para mostrar las matrices del grafo (adyacencia y pesos)
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class VentanaMatriz extends JFrame {

    public VentanaMatriz(Grafo grafo, boolean esMatrizAdyacencia) {
        configurarVentana(esMatrizAdyacencia);
        construirUI(grafo, esMatrizAdyacencia);
    }

    private void configurarVentana(boolean esMatrizAdyacencia) {
        String titulo = esMatrizAdyacencia ? "Matriz de Adyacencia" : "Matriz de Pesos";
        setTitle(titulo);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            Image icono = GestorImagenes.cargarIconoAplicacionEscalado(64, 64);
            if (icono != null) {
                setIconImage(icono);
            }
        } catch (Exception e) {
            System.err.println("Error al configurar el icono: " + e.getMessage());
        }
    }

    private void construirUI(Grafo grafo, boolean esMatrizAdyacencia) {
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String matrizTexto = generarTextoMatriz(grafo, esMatrizAdyacencia);
        textArea.setText(matrizTexto);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                esMatrizAdyacencia ? "Matriz de Adyacencia" : "Matriz de Pesos (Coeficientes)"
        ));

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));

        String info = String.format("Total de nodos: %d", grafo.getContadorNodos());
        JLabel lblInfo = new JLabel(info);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInfo.add(lblInfo);

        panelPrincipal.add(panelInfo, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private String generarTextoMatriz(Grafo grafo, boolean esMatrizAdyacencia) {
        int[][] matriz = esMatrizAdyacencia ?
                grafo.getMatrizAdyacencia() : grafo.getMatrizPesos();

        int totalNodos = grafo.getContadorNodos();
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int i = 0; i < totalNodos; i++) {
            sb.append(String.format("%5d", i));
        }
        sb.append("\n");

        sb.append("    ");
        sb.append("-----".repeat(Math.max(0, totalNodos)));
        sb.append("\n");

        for (int i = 0; i < totalNodos; i++) {
            sb.append(String.format("%2d |", i));

            for (int j = 0; j < totalNodos; j++) {
                sb.append(String.format("%5d", matriz[i][j]));
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
