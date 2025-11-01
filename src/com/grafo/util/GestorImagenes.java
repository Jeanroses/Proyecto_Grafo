package com.grafo.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Utilidad para cargar, gestionar y exportar imágenes
 * @author Jean Paul Purihuaman De La Cruz
 */
public class GestorImagenes {

    public static ImageIcon cargarImagenDeFondo(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen de Fondo");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imágenes (*.jpg, *.png, *.gif)", "jpg", "png", "gif", "jpeg"
        ));

        int resultado = fileChooser.showOpenDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                return new ImageIcon(archivo.getAbsolutePath());
            } catch (Exception ex) {
                DialogosModernos.mostrarMensaje(parent,
                    "Error al cargar la imagen:\n" + ex.getMessage(),
                    "Error de Carga",
                    DialogosModernos.TipoMensaje.ERROR);
            }
        }

        return null;
    }

    public static ImageIcon escalarImagen(ImageIcon icon, int ancho, int alto) {
        if (icon == null) return null;

        Image imagen = icon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    public static void removerImagenDeFondo(JLabel labelImagen) {
        if (labelImagen != null) {
            labelImagen.setIcon(null);
        }
    }

    public static boolean exportarPNG(Component parent, JComponent componente) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar como PNG");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes PNG (*.png)", "png"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                if (!ruta.endsWith(".png")) {
                    ruta += ".png";
                }

                // Crear imagen del componente
                BufferedImage imagen = new BufferedImage(
                        componente.getWidth(),
                        componente.getHeight(),
                        BufferedImage.TYPE_INT_RGB);

                Graphics2D g2d = imagen.createGraphics();
                componente.paint(g2d);
                g2d.dispose();

                // Guardar imagen
                ImageIO.write(imagen, "png", new File(ruta));

                DialogosModernos.mostrarMensaje(parent,
                        "Imagen exportada exitosamente en:\n" + ruta,
                        "Exportar PNG",
                        DialogosModernos.TipoMensaje.EXITO);
                return true;
            } catch (Exception ex) {
                DialogosModernos.mostrarMensaje(parent,
                        "Error al exportar la imagen:\n" + ex.getMessage(),
                        "Error al Exportar",
                        DialogosModernos.TipoMensaje.ERROR);
                return false;
            }
        }
        return false;
    }
}
