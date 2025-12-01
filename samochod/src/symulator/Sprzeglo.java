package symulator;

public class Sprzeglo extends Komponent {
    boolean stanSprzegla;

    public Sprzeglo(boolean stanSprzegla, String producent, String model ) {
        super(producent, model);
        this.stanSprzegla = stanSprzegla;
    }

    void wcisnij(){
        stanSprzegla = true;
    }

    void zwolnij(){
        stanSprzegla = false;
    }
}