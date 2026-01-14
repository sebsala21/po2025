package zadania;
import animals.*;
import java.util.Random;

public class Zoo {
    private Animal[] animals = new Animal[100];
    public void fillZoo() {
        Random random = new Random();

        for (int i = 0; i < animals.length; i++) {
            int type = random.nextInt(3);

            if (type == 0) {
                animals[i]= new Parrot("Parrot " + i);
            } else if (type == 1) {
                animals[i] = new Dog("Dog " + i);
            } else {
                animals[i]= new Snake("Snake " + i);
            }
        }

    }
    public int getTotalLegs() {
        int sum =0;
        for (Animal animal : animals) {
            sum += animal.getLegs();
        }
        return sum;
    }
    public void makeAllSounds() {
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }

    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        zoo.fillZoo();
        //do obs getDescr
        for (Animal animal : zoo.animals) {
            System.out.println(animal.getDescription());
        }

        System.out.println("Total legs in zoo: " + zoo.getTotalLegs());
        zoo.makeAllSounds();
    }

}
