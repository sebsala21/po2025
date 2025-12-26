package symulator;

public class Silnik extends Komponent {
    private int maxObroty;
    private int obroty;
    public Silnik(String producent, String model) {
        super(producent, model);
        this.maxObroty=6000;
        this.obroty=0;
    }
    public void uruchom() {
        this.obroty=800;
        System.out.println("Silnik został uruchomionyObroty to:" +obroty);
    }
    public void zatrzymaj() {
        this.obroty=0;
        System.out.println("Silnik został zgaszony");
    }

}

