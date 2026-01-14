package animals;

public class Dog extends Animal {
    public Dog(String name){
        super(name,4);
    }

    public String getDescription() {
        return name + "jest psem z" + legs + "nogami";

    }
    public void makeSound() {
        System.out.println(name + " mowi: hau hau");
    }

}
