public class Choinka {
    public static void main(String[] args) {
        int wysokosc = 20;
        
        for (int i = 1; i <= wysokosc; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}