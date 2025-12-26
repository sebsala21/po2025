package symulator;

public class SkrzyniaBiegow extends Komponent{
private int aktualnyBieg;
private int iloscBiegow;
    public SkrzyniaBiegow(String producent, String model){
        super(producent,model);
        this.aktualnyBieg=0;
        this.iloscBiegow=6;
    }
   public void zwiekszBieg() {
        if (aktualnyBieg < iloscBiegow){
            aktualnyBieg++;
            System.out.println("wrzucono wyższy bieg" + aktualnyBieg);
        }
        else {
            System.out.println("Nie mozna wrzucić wyższego biegu");
        }
   }
   public void zmniejszBieg(){
        if (aktualnyBieg > -1) {
            aktualnyBieg--;
            System.out.println("żmniejszono biego aktualny bieg to " + aktualnyBieg);
        }
        else{
            System.out.println("nie da sie zmniejszyćtego biegu bo to jest minimalny bieg");
        }

   }
    public void ustawBieg(int bieg){
        this.aktualnyBieg=bieg;
        System.out.println("Zmieniono biego na" + aktualnyBieg);
    }

}
