
public class Mammal extends Creature {

    public Mammal(String name, int row, int col) {
        super(name, row, col, 25);
    }

    @Override
    public void takeTurn(World world) {
        // TODO: mammals forage — scan the neighboring tiles; if one holds a
        // Plant, eat it (clear it from its tile and gain energy). Otherwise
        // wander to a random empty neighboring tile. Moving costs energy.

        // Look for plants in neighboring tiles
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {

                if (r == row && c == col) {
                    continue;
                }

                if (world.inBounds(r, c)) {
                    Tile tile = world.getTile(r, c);

                    for (Creature creature :
                            new java.util.ArrayList<Creature>(tile.getCreatures())) {

                        if (creature instanceof Plant) {
                            tile.removeCreature(creature);
                            world.getCreatures().remove(creature);
                            energy = energy + 5;
                            System.out.println(name + " ate a plant!");
                            return;
                        }
                    }
                }
            }
        }

        // Move randomly if no plant was found
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
        // TODO: return something like "Mammal: <name> at (row,col) energy=<energy>"
        return "Mammal: " + name + " at (" + row + "," + col
                + ") energy=" + energy;
    }

    @Override
    public char symbol() { return 'M'; }
}
