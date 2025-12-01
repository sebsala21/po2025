package symulator;

public class Pozycja {
    double x;
    double y;

    public Pozycja(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void aktualizujPozycje(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    String getPozycja(double x, double y) {
        return x + " " + y;
    }
}