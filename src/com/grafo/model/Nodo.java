package com.grafo.model;

/**
 * Clase que representa un nodo en el grafo
 *
 * @author Jean Paul Purihuaman De La Cruz
 */
public class Nodo {
    private int id;
    private int x;
    private int y;
    private boolean visitado;
    private boolean etiquetado;
    private int distanciaAcumulada;
    private Nodo predecesor;

    public Nodo(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.visitado = false;
        this.etiquetado = false;
        this.distanciaAcumulada = 0;
        this.predecesor = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean isEtiquetado() {
        return etiquetado;
    }

    public void setEtiquetado(boolean etiquetado) {
        this.etiquetado = etiquetado;
    }

    public int getDistanciaAcumulada() {
        return distanciaAcumulada;
    }

    public void setDistanciaAcumulada(int distanciaAcumulada) {
        this.distanciaAcumulada = distanciaAcumulada;
    }

    public Nodo getPredecesor() {
        return predecesor;
    }

    public void setPredecesor(Nodo predecesor) {
        this.predecesor = predecesor;
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nodo nodo = (Nodo) o;
        return id == nodo.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

