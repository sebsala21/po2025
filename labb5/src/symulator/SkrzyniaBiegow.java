package symulator;

public class SkrzyniaBiegow extends Komponent {
    int aktualnyBieg;
    int iloscBiegow;
    Sprzeglo sprzeglo;

    public SkrzyniaBiegow(int aktualnyBieg, int iloscBiegow,Sprzeglo sprzeglo, String producent, String model) {
        super(producent, model);
        this.aktualnyBieg = aktualnyBieg;
        this.iloscBiegow = iloscBiegow;
        this.sprzeglo = sprzeglo;
    }

    void zwiekszBieg() {
        if (aktualnyBieg < iloscBiegow) {
            aktualnyBieg++;
        }
    }
    void zmniejszBieg() {
        if (aktualnyBieg > 0) {
            aktualnyBieg--;
        }
    }
}