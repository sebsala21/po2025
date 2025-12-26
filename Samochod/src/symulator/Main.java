package symulator;

public class Main {
    public static void main(String[] args) {

        System.out.println("--- START TESTU ---");
        Samochod mojeAuto = new Samochod();
        mojeAuto.wlacz();


        System.out.println("\n--- Przyspieszamy (1-6) ---");
        mojeAuto.getSkrzynia().zwiekszBieg(); // 1
        mojeAuto.getSkrzynia().zwiekszBieg(); // 2
        mojeAuto.getSkrzynia().zwiekszBieg(); // 3
        mojeAuto.getSkrzynia().zwiekszBieg(); // 4
        mojeAuto.getSkrzynia().zwiekszBieg(); // 5
        mojeAuto.getSkrzynia().zwiekszBieg(); // 6


        System.out.println("\n--- Próba wrzucenia 7 biegu ---");
        mojeAuto.getSkrzynia().zwiekszBieg();


        System.out.println("\n--- Hamujemy ---");
        mojeAuto.getSkrzynia().zmniejszBieg();
        mojeAuto.getSkrzynia().zmniejszBieg();

        mojeAuto.getSkrzynia().ustawBieg(0);
        System.out.println("Szybki zjazd na luz (0)");


        System.out.println("\n--- Wsteczny ---");
        mojeAuto.getSkrzynia().zmniejszBieg();


        System.out.println("\n--- Próba przebicia podłogi (-2) ---");
        mojeAuto.getSkrzynia().zmniejszBieg();

        System.out.println("\n--- KONIEC TESTU ---");
        System.out.println("\n--- ZMIANA BIEGU ZE SPRZĘGŁEM ---");


        mojeAuto.getSprzeglo().wcisnij();


        mojeAuto.getSkrzynia().zwiekszBieg();


        mojeAuto.getSprzeglo().zwolnij();


        System.out.println("Jazda!");
    }

}