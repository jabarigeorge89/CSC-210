
public class Tree extends Plant {

    public Tree(String name, int row, int col, int energy) {
        super(name, row, col, energy);
    }

    @Override
    protected void grow(World world) {
        System.out.println(name + " is growing.");
    }

    @Override
    public String describe() {
        return name + " is a tree with " + energy + " energy.";
    }

    @Override
    public char symbol() {
        return 'T';
    }
}
