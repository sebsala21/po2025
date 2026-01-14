package animals;

public class Parrot extends Animal {

    public Parrot(String name){
        super(name,2);
    }

    public String getDescription() {
        return name + "jest papuga z" + legs + "nogami";
    }

    public void makeSound() {
        System.out.println(name + " mowi: cwir cwir");
    }
}
