
public class Fish extends Creature {

    public Fish(String name, int row, int col) {
        super(name, row, col, 15);
    }

    @Override
    public void takeTurn(World world) {
        // TODO: fish swim — pick a random orthogonal direction (no
        // diagonals); if the destination tile is in bounds and empty, move
        // there with world.moveCreature(...). Moving costs energy.
        // Consider using the tile's water attribute: fish should prefer
        // (or require) wet tiles.

        int direction = (int)(Math.random() * 4);

        int newRow = row;
        int newCol = col;

        if (direction == 0) {
            newRow--;
        } else if (direction == 1) {
            newRow++;
        } else if (direction == 2) {
            newCol--;
        } else {
            newCol++;
        }

        if (world.inBounds(newRow, newCol)) {
            Tile tile = world.getTile(newRow, newCol);

            if (tile.isEmpty() && tile.getWater() > 0) {
                world.moveCreature(this, newRow, newCol);
                energy = energy - 1;
            }
        }
    }

    @Override
    public String describe() {
        // TODO: return something like "Fish: <name> at (row,col) energy=<energy>"
        return "Fish: " + name;
    }

    @Override
    public char symbol() { return 'F'; }
}
