
import java.util.ArrayList;
import java.util.List;

/**
 * A 2-dimensional grid of Tiles plus the master turn loop.
 */
public class World {
    private int rows;
    private int cols;
    private Tile[][] grid;
    private List<Creature> creatures;
    private int turn;

    public World(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Tile[rows][cols];
        this.creatures = new ArrayList<>();
        this.turn = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Tile();
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public Tile getTile(int r, int c) { return grid[r][c]; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getTurn() { return turn; }
    public List<Creature> getCreatures() { return creatures; }

    public void addCreature(Creature c, int row, int col) {
        // TODO: if (row, col) is in bounds and that tile has room, set the
        // creature's position, place it on the tile, and track it in the
        // creatures list.

        if (c != null && inBounds(row, col)) {
            c.setRow(row);
            c.setCol(col);
            grid[row][col].setOccupant(c);
            creatures.add(c);
        }
    }

    public void moveCreature(Creature c, int newRow, int newCol) {
        // TODO: remove the creature from its current tile, update its
        // row/col, and place it on the destination tile.

        if (c != null && inBounds(newRow, newCol)
                && creatures.contains(c)) {

            grid[c.getRow()][c.getCol()].removeCreature(c);

            c.setRow(newRow);
            c.setCol(newCol);

            grid[newRow][newCol].setOccupant(c);
        }
    }

    public void takeTurn() {
        // TODO: advance the turn counter, then iterate through every Tile
        // and call its takeTurn(...) method (each Tile in turn calls
        // takeTurn() on the creatures it holds). Afterwards, remove dead
        // creatures (isAlive() == false) from their tiles and from the
        // creatures list.
        //
        // Hint: creatures may be added or removed mid-turn (seeds sprout,
        // plants get eaten) — iterate over a snapshot copy to avoid
        // ConcurrentModificationException.

        turn++;

        List<Creature> snapshot = new ArrayList<>(creatures);

        for (Creature c : snapshot) {
            if (creatures.contains(c) && c.isAlive()) {
                c.takeTurn(this);
            }
        }

        for (Creature c : new ArrayList<>(creatures)) {
            if (!c.isAlive()) {
                grid[c.getRow()][c.getCol()].removeCreature(c);
                creatures.remove(c);
            }
        }
    }

    public void print() {
        System.out.println("Turn " + turn + "  Creatures: " + creatures.size());
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(grid[r][c].symbol());
            }
            System.out.println();
        }
    }
}
