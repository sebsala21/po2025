package com.example.samochodgui.symulator;

public class Pozycja {
    private double x;
    private double y;

    public Pozycja() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Pozycja(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void aktualizujPozycje(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    public void ustaw(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String getPozycja() {
        return "X: " + x + ", Y: " + y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
