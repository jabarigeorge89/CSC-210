
public class Flower extends Plant {

    public Flower(String name, int row, int col, int energy) {
        super(name, row, col, energy);
    }

    @Override
    protected void grow(World world) {
        System.out.println(name + " is blooming.");
    }

    @Override
    public String describe() {
        return name + " is a flower with " + energy + " energy.";
    }

    @Override
    public char symbol() {
        return 'F';
    }
}
