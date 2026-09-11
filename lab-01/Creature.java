public class Creature {

    String name;
    int size;

    public Creature(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void talk() {
        System.out.println(name + " says hello.");
    }

    public void move() {
        System.out.println(name + " is moving.");
    }

    public static void main(String[] args) {

        Creature animal = new Creature("Tiger", 5);

        animal.eat();
        animal.talk();
        animal.move();
    }
}