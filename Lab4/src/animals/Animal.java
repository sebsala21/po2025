package animals;

public abstract class Animal {
    protected String name;
    protected int legs;

    //Konstruktor
    public Animal(String name, int legs) {
        this.name = name;
        this.legs = legs;
    }

    public abstract String getDescription();

    //Getter
    public int getLegs() {
        return legs;
    }

    public String getName() {
        return name;
    }
    public void makeSound() {
        System.out.println("dzwiek");
    }


}
