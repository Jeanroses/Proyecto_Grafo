package com.grafo.util;

import javax.swing.*;
import java.awt.*;

public class ValidadorEntrada {

    public static int solicitarNodoOrigen(Component parent, String mensaje, int maxNodos) throws IllegalStateException {
        while (true) {
            String input = DialogosModernos.mostrarDialogoEntrada(parent, mensaje, "Selección de Nodo");

            if (input == null || input.trim().isEmpty()) {
                throw new IllegalStateException("Operación cancelada");
            }

            try {
                int nodo = Integer.parseInt(input.trim());

                if (nodo < 0 || nodo >= maxNodos) {
                    DialogosModernos.mostrarMensaje(parent,
                            "El nodo debe estar entre 0 y " + (maxNodos - 1),
                            "Nodo Inválido",
                            DialogosModernos.TipoMensaje.ERROR);
                    continue;
                }

                return nodo;
            } catch (NumberFormatException e) {
                DialogosModernos.mostrarMensaje(parent,
                        "Debe ingresar un número válido",
                        "Error de Formato",
                        DialogosModernos.TipoMensaje.ERROR);
            }
        }
    }

    public static int solicitarPeso(Component parent, String mensaje) throws IllegalStateException {
        while (true) {
            String input = DialogosModernos.mostrarDialogoEntrada(parent, mensaje, "Peso de Arista");

            if (input == null || input.trim().isEmpty()) {
                throw new IllegalStateException("Operación cancelada");
            }

            try {
                int peso = Integer.parseInt(input.trim());

                if (peso <= 0) {
                    DialogosModernos.mostrarMensaje(parent,
                            "El peso debe ser mayor a 0",
                            "Peso Inválido",
                            DialogosModernos.TipoMensaje.ERROR);
                    continue;
                }

                return peso;
            } catch (NumberFormatException e) {
                DialogosModernos.mostrarMensaje(parent,
                        "Debe ingresar un número válido",
                        "Error de Formato",
                        DialogosModernos.TipoMensaje.ERROR);
            }
        }
    }

    public static boolean validarNodosDiferentes(Component parent, int nodo1, int nodo2) {
        if (nodo1 == nodo2) {
            DialogosModernos.mostrarMensaje(parent,
                    "Los nodos deben ser diferentes",
                    "Nodos Iguales",
                    DialogosModernos.TipoMensaje.ADVERTENCIA);
            return false;
        }
        return true;
    }
}
