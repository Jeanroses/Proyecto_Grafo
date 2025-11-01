package com.grafo.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Componentes y utilidades de UI moderna para Swing

 * @author Jean Paul Purihuaman De La Cruz
 */
public final class ModernUI {

    private ModernUI() {}

    public static final Color PRIMARY = new Color(52, 152, 219);
    public static final Color PRIMARY_DARK = new Color(41, 128, 185);
    public static final Color SUCCESS = new Color(46, 204, 113);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color PURPLE = new Color(155, 89, 182);
    public static final Color TEXT_DARK = new Color(44, 62, 80);
    public static final Color PANEL_BG = new Color(246, 248, 250);
    public static final Color CARD_BG = new Color(236, 240, 241);


    public static void applyGlobalFont(String family, int size) {

        Font uiFont = new Font(family, Font.PLAIN, size);


        Font symbolFont = new Font("Segoe UI Symbol", Font.PLAIN, size);

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Map<Object, Object> defaults = new HashMap<>();
        defaults.put("Button.font", uiFont);
        defaults.put("Label.font", uiFont);
        defaults.put("TextField.font", uiFont);
        defaults.put("TextArea.font", uiFont);
        defaults.put("ComboBox.font", uiFont);
        defaults.put("Menu.font", uiFont);
        defaults.put("MenuItem.font", uiFont);
        defaults.put("CheckBox.font", uiFont);
        defaults.put("RadioButton.font", uiFont);
        defaults.put("TitledBorder.font", uiFont.deriveFont(Font.BOLD, (float)size));

        defaults.put("swing.boldMetal", Boolean.FALSE);
        defaults.put("awt.useSystemAAFontSettings", "on");
        defaults.put("swing.aatext", Boolean.TRUE);

        for (Map.Entry<Object, Object> e : defaults.entrySet()) {
            UIManager.put(e.getKey(), e.getValue());
        }
    }

    public static JLabel iconLabel(String text, String iconChar) {
        JLabel label = new JLabel(iconChar + "  " + text);
        label.setForeground(TEXT_DARK);
        label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        label.setBorder(new EmptyBorder(2, 2, 2, 2));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_DARK);
        label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        label.setBorder(new EmptyBorder(6, 0, 6, 0));
        return label;
    }

    public static JMenuItem menuItemWithIcon(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        return item;
    }

    public static JPanel cardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(4, 6, w - 8, h - 8, 16, 16);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, w - 8, h - 8, 16, 16);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(14, 16, 14, 16));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    public static JButton modernButton(String text, Color base) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(base);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));

        Color hover = base.darker();
        Color press = base.darker().darker();

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateBackground(btn, btn.getBackground(), hover, 120);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                animateBackground(btn, btn.getBackground(), base, 120);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(press);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                animateBackground(btn, btn.getBackground(), hover, 120);
            }
        });
        return btn;
    }

    private static void animateBackground(JComponent comp, Color from, Color to, int ms) {
        int frames = 12;
        int delay = Math.max(10, ms / frames);
        Timer t = new Timer(delay, null);
        final int[] step = {0};
        t.addActionListener(e -> {
            float p = (step[0]++) / (float) frames;
            if (p >= 1f) { comp.setBackground(to); t.stop(); return; }
            comp.setBackground(interpolate(from, to, p));
            comp.repaint();
        });
        t.start();
    }

    private static Color interpolate(Color a, Color b, float p) {
        int r = (int)(a.getRed() + (b.getRed() - a.getRed()) * p);
        int g = (int)(a.getGreen() + (b.getGreen() - a.getGreen()) * p);
        int bl = (int)(a.getBlue() + (b.getBlue() - a.getBlue()) * p);
        return new Color(r, g, bl);
    }
}
