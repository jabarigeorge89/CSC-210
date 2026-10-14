/**
 * Abstract base class for all plants.
 *
 * TODO (Core): create at least 3 concrete Plant subclasses of your own
 * (e.g. Tree, Flower, Moss), plus any interfaces and component classes you
 * need. Favor COMPOSITION over inheritance for behaviors such as seeding.
 * Plant (or a subclass) must OVERRIDE at least one Creature method.
 */
public abstract class Plant extends Creature {

    public Plant(String name, int row, int col, int energy) {
        super(name, row, col, energy);
    }

    @Override
public void takeTurn(World world) {
    energy = energy + 1;
    grow(world);
}

    /** Species-specific growth/reproduction behavior. */
    protected abstract void grow(World world);
}
