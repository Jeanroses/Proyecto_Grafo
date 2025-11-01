package com.grafo.util;

import com.grafo.model.Grafo;
import com.grafo.ui.PanelGrafo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Utilidad para exportar grafos a diferentes formatos
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class ExportadorGrafo {

    public static void exportarComoImagen(Component parent, PanelGrafo panelGrafo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Grafo como Imagen");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagen PNG (*.png)", "png"));

        int resultado = fileChooser.showSaveDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                if (!archivo.getName().toLowerCase().endsWith(".png")) {
                    archivo = new File(archivo.getAbsolutePath() + ".png");
                }

                BufferedImage imagen = new BufferedImage(
                    panelGrafo.getWidth(),
                    panelGrafo.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
                );

                Graphics2D g2d = imagen.createGraphics();
                panelGrafo.paint(g2d);
                g2d.dispose();

                ImageIO.write(imagen, "png", archivo);

                DialogosModernos.mostrarMensaje(parent,
                    "Imagen guardada exitosamente en:\n" + archivo.getAbsolutePath(),
                    "Exportación Exitosa",
                    DialogosModernos.TipoMensaje.EXITO);

            } catch (IOException ex) {
                DialogosModernos.mostrarMensaje(parent,
                    "Error al guardar la imagen:\n" + ex.getMessage(),
                    "Error de Exportación",
                    DialogosModernos.TipoMensaje.ERROR);
            }
        }
    }

    public static void guardarGrafo(Component parent, Grafo grafo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Grafo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo de Grafo (*.grafo)", "grafo"));

        int resultado = fileChooser.showSaveDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                if (!archivo.getName().toLowerCase().endsWith(".grafo")) {
                    archivo = new File(archivo.getAbsolutePath() + ".grafo");
                }

                try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
                    writer.println("NODOS:" + grafo.getContadorNodos());

                    writer.println("POSICIONES:");
                    for (int i = 0; i < grafo.getContadorNodos(); i++) {
                        writer.println(i + "," + grafo.getNodo(i).getX() + "," + grafo.getNodo(i).getY());
                    }

                    writer.println("ARISTAS:");
                    for (int i = 0; i < grafo.getContadorNodos(); i++) {
                        for (int j = i + 1; j < grafo.getContadorNodos(); j++) {
                            if (grafo.existeArista(i, j)) {
                                writer.println(i + "," + j + "," + grafo.getPeso(i, j));
                            }
                        }
                    }
                }

                DialogosModernos.mostrarMensaje(parent,
                    "Grafo guardado exitosamente",
                    "Guardado Exitoso",
                    DialogosModernos.TipoMensaje.EXITO);

            } catch (IOException ex) {
                DialogosModernos.mostrarMensaje(parent,
                    "Error al guardar el grafo:\n" + ex.getMessage(),
                    "Error de Guardado",
                    DialogosModernos.TipoMensaje.ERROR);
            }
        }
    }

    public static Grafo cargarGrafo(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargar Grafo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo de Grafo (*.grafo)", "grafo"));

        int resultado = fileChooser.showOpenDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                Grafo grafo = new Grafo();

                try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    String seccion = "";

                    while ((linea = reader.readLine()) != null) {
                        linea = linea.trim();

                        if (linea.startsWith("NODOS:")) {
                            continue;
                        } else if (linea.equals("POSICIONES:")) {
                            seccion = "POSICIONES";
                        } else if (linea.equals("ARISTAS:")) {
                            seccion = "ARISTAS";
                        } else if (!linea.isEmpty()) {
                            String[] partes = linea.split(",");

                            if (seccion.equals("POSICIONES")) {
                                int id = Integer.parseInt(partes[0]);
                                int x = Integer.parseInt(partes[1]);
                                int y = Integer.parseInt(partes[2]);
                                grafo.agregarNodo(x, y);
                            } else if (seccion.equals("ARISTAS")) {
                                int origen = Integer.parseInt(partes[0]);
                                int destino = Integer.parseInt(partes[1]);
                                int peso = Integer.parseInt(partes[2]);
                                grafo.agregarArista(origen, destino, peso);
                            }
                        }
                    }
                }

                DialogosModernos.mostrarMensaje(parent,
                    "Grafo cargado exitosamente",
                    "Carga Exitosa",
                    DialogosModernos.TipoMensaje.EXITO);

                return grafo;

            } catch (Exception ex) {
                DialogosModernos.mostrarMensaje(parent,
                    "Error al cargar el grafo:\n" + ex.getMessage(),
                    "Error de Carga",
                    DialogosModernos.TipoMensaje.ERROR);
            }
        }

        return null;
    }
}
