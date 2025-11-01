package com.grafo.util;

import com.grafo.model.EstadoGrafo;
import com.grafo.model.Grafo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

/**
 * Clase para gestionar operaciones de archivos (guardar y abrir grafos)
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class GestorArchivos {

    public static boolean guardarGrafo(Component parent, Grafo grafo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Grafo");
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Archivos de Grafo (*.grafo)", "grafo"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                if (!ruta.endsWith(".grafo")) {
                    ruta += ".grafo";
                }

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
                    oos.writeObject(new EstadoGrafo(grafo));
                }

                DialogosModernos.mostrarMensaje(parent,
                        "Grafo guardado exitosamente en:\n" + ruta,
                        "Guardar Grafo",
                        DialogosModernos.TipoMensaje.EXITO);
                return true;
            } catch (Exception ex) {
                DialogosModernos.mostrarMensaje(parent,
                        "Error al guardar el grafo:\n" + ex.getMessage(),
                        "Error al Guardar",
                        DialogosModernos.TipoMensaje.ERROR);
                return false;
            }
        }
        return false;
    }

    public static boolean abrirGrafo(Component parent, Grafo grafo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir Grafo");
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Archivos de Grafo (*.grafo)", "grafo"));

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(fileChooser.getSelectedFile()))) {
                    EstadoGrafo estado = (EstadoGrafo) ois.readObject();
                    estado.restaurarEstado(grafo);
                }

                DialogosModernos.mostrarMensaje(parent,
                        "Grafo cargado exitosamente con " + grafo.getContadorNodos() + " nodos",
                        "Abrir Grafo",
                        DialogosModernos.TipoMensaje.EXITO);
                return true;
            } catch (Exception ex) {
                DialogosModernos.mostrarMensaje(parent,
                        "Error al abrir el grafo:\n" + ex.getMessage(),
                        "Error al Abrir",
                        DialogosModernos.TipoMensaje.ERROR);
                return false;
            }
        }
        return false;
    }

    public static String obtenerRutaGuardar(Component parent, String titulo, String extension, String descripcion) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileFilter(new FileNameExtensionFilter(descripcion, extension));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            if (!ruta.endsWith("." + extension)) {
                ruta += "." + extension;
            }
            return ruta;
        }
        return null;
    }
}
