package symulator;

public class Samochod {
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Sprzeglo sprzeglo;
    private Pozycja pozycja;

    public Samochod() {
        this.silnik= new Silnik("TDI","2.0 DIseal");
        this.skrzynia= new SkrzyniaBiegow("Manualna","6-biegowa");
        this.sprzeglo= new Sprzeglo("Sachs","Sportowe");
        this.pozycja= new Pozycja();
    }
    public void wlacz(){
        System.out.println("Przekręcam kluczyk");
        silnik.uruchom();
    }
    public void wylacz(){
        System.out.println("wyciagam kluczyk");
        silnik.zatrzymaj();
        skrzynia.ustawBieg(0);
    }
    public Pozycja getPozycja() {
        return pozycja;
    }
    public SkrzyniaBiegow getSkrzynia(){
        return skrzynia;
    }
    public Sprzeglo getSprzeglo(){
        return sprzeglo;
    }
}
