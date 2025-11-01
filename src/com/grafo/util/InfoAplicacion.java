package com.grafo.util;

/**
 * Información de la aplicación y metadatos
 *
 * @author Jean Paul Purihuaman De La Cruz
 * @version 2.0
 * @since 2025
 */
public class InfoAplicacion {

    public static final String NOMBRE = "Sistema de Grafos";
    public static final String VERSION = "2.0";
    public static final String FECHA_VERSION = "2025";

    public static final String AUTOR = "Jean Paul Purihuaman De La Cruz";
    public static final String EMAIL = "jeanpaulpurihuamandelacruz2019.com";
    public static final String GITHUB = "https://github.com/Jeanroses";


    public static final String CURSO = "Inteligencia Artificial";
    public static final String INSTITUCION = "Universidad Nacional Pedro Ruiz Gallo";

    public static final String COPYRIGHT = "© 2025 Jean Paul Purihuaman De La Cruz. Todos los derechos reservados.";
    public static final String LICENCIA = "MIT License";

    public static final String DESCRIPCION =
        "Sistema interactivo para crear, visualizar y ejecutar algoritmos sobre grafos.\n" +
        "Incluye implementaciones de Dijkstra, BFS, DFS y Prim.";

    public static final String[] CARACTERISTICAS = {
        "Creación interactiva de grafos",
        "Algoritmo de Dijkstra para caminos más cortos",
        "Búsqueda en Anchura (BFS)",
        "Búsqueda en Profundidad (DFS)",
        "Algoritmo de Prim para árbol de recubrimiento mínimo",
        "Visualización en tiempo real",
        "Exportación a PNG",
        "Guardado y carga de grafos",
        "Historial con Deshacer/Rehacer",
        "Modo oscuro",
        "Zoom y navegación"
    };

    public static String getInfoCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("  ").append(NOMBRE).append(" v").append(VERSION).append("\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        sb.append("Autor: ").append(AUTOR).append("\n");
        sb.append("GitHub: ").append(GITHUB).append("\n");
        sb.append("Curso: ").append(CURSO).append("\n\n");
        sb.append(COPYRIGHT).append("\n");
        sb.append("Licencia: ").append(LICENCIA).append("\n\n");
        sb.append("Descripción:\n").append(DESCRIPCION).append("\n\n");
        sb.append("Características:\n");
        for (String caracteristica : CARACTERISTICAS) {
            sb.append("  • ").append(caracteristica).append("\n");
        }
        sb.append("\n═══════════════════════════════════════════════════════════\n");
        return sb.toString();
    }

    public static String getCopyrightLinea() {
        return COPYRIGHT;
    }

    public static String getEncabezadoArchivo() {
        return String.format(
            "/*\n" +
            " * %s v%s\n" +
            " * %s\n" +
            " * Autor: %s\n" +
            " * GitHub: %s\n" +
            " */\n",
            NOMBRE, VERSION, COPYRIGHT, AUTOR, GITHUB
        );
    }

    public static void mostrarInfo() {
        System.out.println(getInfoCompleta());
    }
}

