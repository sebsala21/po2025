package symulator;

public class Pozycja {
    private double x;
    private double y;
    public Pozycja(){
        this.x=0.0;
        this.y=0.0;
    }
    public void aktualizujPozycje(double deltaX, double deltaY  ){
        this.x += deltaX;
        this.y += deltaY;
    }
    public String getPozycja(){
        return "X: " + x + ", Y: " + y;
    }
}
