

/**
 * Abstract base class for everything living in the world.
 *
 * Core requirements handled here:
 *   - Creature is abstract.
 *   - Subclasses must chain to the constructor below with super(...).
 *
 * TODO (Core, challenging): add at least one OVERLOADED constructor to a
 * subclass (two different parameter lists), and make each constructor call
 * super(...) so shared initialization lives in one place.
 */
public abstract class Creature implements TurnTaker {
    protected String name;
    protected int row;
    protected int col;
    protected int energy;

    // ------------------------------------------------------------------
    // TODO (Stretch) — species registry:
    //   - Add a String `species` attribute. The species name uniquely
    //     identifies a creature with particular properties even when that
    //     species is not a distinct subclass.
    //   - Add a private static collection (e.g. HashMap<String, String>)
    //     that keeps track of every species that has been invented, along
    //     with a string that uniquely defines how to build that species.
    //   - Add a static method, e.g. registerSpecies(String name, String
    //     definition), that adds and defines the properties of a species.
    //     If a species with that name ALREADY EXISTS, the method must
    //     THROW an Exception and not create that kind of creature.
    // ------------------------------------------------------------------

    public Creature(String name, int row, int col, int energy) {
        this.name   = name;
        this.row    = row;
        this.col    = col;
        this.energy = energy;
    }

    public String getName()  { return name; }
    public int getRow()      { return row; }
    public int getCol()      { return col; }
    public int getEnergy()   { return energy; }

    public void setRow(int r) { row = r; }
    public void setCol(int c) { col = c; }
    public void addEnergy(int e) { energy += e; }

    public boolean isAlive() {
        // TODO: a creature is alive while it still has energy.
        return energy > 0;
    }

    /** A one-line human-readable description of this creature. */
    public abstract String describe();

    /** The single character used to draw this creature on the map. */
    public abstract char symbol();
}
