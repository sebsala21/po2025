package animals;

public class Snake extends Animal {

    public Snake(String name){
        super(name,0);
    }
    public String getDescription() {
        return name + "jest wezem z" + legs + "nogami";
    }

    public void makeSound() {
        System.out.println(name + " mowi: sss sss");
    }

}
