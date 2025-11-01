package com.grafo.util;

import com.grafo.model.EstadoGrafo;
import com.grafo.model.Grafo;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Gestor de historial para Deshacer/Rehacer
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class GestorHistorial {
    private final Deque<EstadoGrafo> historialDeshacer;
    private final Deque<EstadoGrafo> historialRehacer;
    private final int capacidadMaxima;

    public GestorHistorial(int capacidadMaxima) {
        this.historialDeshacer = new ArrayDeque<>();
        this.historialRehacer = new ArrayDeque<>();
        this.capacidadMaxima = capacidadMaxima;
    }

    public GestorHistorial() {
        this(50);
    }

    public void guardarEstado(Grafo grafo) {
        EstadoGrafo estado = new EstadoGrafo(grafo);

        if (historialDeshacer.size() >= capacidadMaxima) {
            historialDeshacer.removeFirst();
        }

        historialDeshacer.addLast(estado);
        historialRehacer.clear();
    }

    public boolean deshacer(Grafo grafo) {
        if (!puedeDeshacer()) {
            return false;
        }

        EstadoGrafo estadoActual = new EstadoGrafo(grafo);
        historialRehacer.addLast(estadoActual);

        EstadoGrafo estadoAnterior = historialDeshacer.removeLast();
        estadoAnterior.restaurarEstado(grafo);

        return true;
    }

    public boolean rehacer(Grafo grafo) {
        if (!puedeRehacer()) {
            return false;
        }

        EstadoGrafo estadoActual = new EstadoGrafo(grafo);
        historialDeshacer.addLast(estadoActual);

        EstadoGrafo estadoRehecho = historialRehacer.removeLast();
        estadoRehecho.restaurarEstado(grafo);

        return true;
    }

    public boolean puedeDeshacer() {
        return !historialDeshacer.isEmpty();
    }

    public boolean puedeRehacer() {
        return !historialRehacer.isEmpty();
    }

    public void limpiar() {
        historialDeshacer.clear();
        historialRehacer.clear();
    }

    public int getTamanoDeshacer() {
        return historialDeshacer.size();
    }

    public int getTamanoRehacer() {
        return historialRehacer.size();
    }
}
