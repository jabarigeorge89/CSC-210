
public class Bird extends Creature {

    public Bird(String name, int row, int col) {
        super(name, row, col, 20);
    }

    @Override
    public void takeTurn(World world) {
        // TODO: birds fly — pick a random direction (including diagonals),
        // and if the destination tile is in bounds and empty, move there
        // with world.moveCreature(...). Moving costs energy.
        // Behavior should also depend on what else is in the tile and on
        // the tile's water/temperature/nutrient attributes.

        int newRow = row + (int)(Math.random() * 3) - 1;
        int newCol = col + (int)(Math.random() * 3) - 1;

        if (world.inBounds(newRow, newCol)) {
            Tile tile = world.getTile(newRow, newCol);

            if (tile.isEmpty()) {
                world.moveCreature(this, newRow, newCol);
                energy = energy - 1;
            }
        }
    }

    @Override
    public String describe() {
        // TODO: return something like "Bird: <name> at (row,col) energy=<energy>"
        return "Bird: " + name;
    }

    @Override
    public char symbol() { return 'B'; }
}
