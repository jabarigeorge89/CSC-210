
public class Moss extends Plant {

    public Moss(String name, int row, int col, int energy) {
        super(name, row, col, energy);
    }

    @Override
    protected void grow(World world) {
        System.out.println(name + " is spreading.");
    }

    @Override
    public String describe() {
        return name + " is moss with " + energy + " energy.";
    }

    @Override
    public char symbol() {
        return 'M';
    }
}
