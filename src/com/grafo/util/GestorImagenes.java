package com.grafo.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Utilidad para cargar, gestionar y exportar imágenes
 * @author Jean Paul Purihuaman De La Cruz
 */
public class GestorImagenes {

    public static ImageIcon cargarIconoAplicacion() {
        try {

            InputStream is = GestorImagenes.class.getResourceAsStream("/com/grafo/resources/icon.png");
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                is.close();
                if (img != null) {
                    return new ImageIcon(img);
                }
            }

            is = GestorImagenes.class.getResourceAsStream("../resources/icon.png");
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                is.close();
                if (img != null) {
                    return new ImageIcon(img);
                }
            }

            URL url = GestorImagenes.class.getResource("/com/grafo/resources/icon.png");
            if (url != null) {
                return new ImageIcon(url);
            }

            File iconFile = new File("src/com/grafo/resources/icon.png");
            if (iconFile.exists()) {
                return new ImageIcon(iconFile.getAbsolutePath());
            }

            iconFile = new File("out/com/grafo/resources/icon.png");
            if (iconFile.exists()) {
                return new ImageIcon(iconFile.getAbsolutePath());
            }

            System.err.println("No se pudo cargar el icono de la aplicación desde ninguna ubicación");
            return null;
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Image cargarIconoAplicacionEscalado(int ancho, int alto) {
        ImageIcon icon = cargarIconoAplicacion();
        if (icon != null) {
            return icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }
        return null;
    }

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
