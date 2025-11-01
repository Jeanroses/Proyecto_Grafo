import com.grafo.ui.VentanaPrincipal;

import javax.swing.*;

/**
 * Clase principal del sistema de grafos
 * @author Jean Paul Purihuaman De La Cruz
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}


