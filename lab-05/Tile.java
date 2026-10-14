

import java.util.ArrayList;

/**
 * One cell of the world.
 *
 * The skeleton stores a single occupant to stay small. Core requirements
 * you must add:
 *   TODO (Core): generalize the occupant to an ArrayList<Creature>.
 *   TODO (Core): give water, temperature, and nutrient real meaning —
 *     initialize them (from the constructor or the config) and make
 *     creatures consult them in their takeTurn() logic.
 *   TODO (Core): implement TurnTaker — takeTurn(World) should go through
 *     this tile's list of creatures and invoke each one's takeTurn().
 */
public class Tile implements TurnTaker {

    private ArrayList<Creature> occupants;

    private int water;
    private int temperature;
    private int nutrient;

    public Tile() {
        occupants = new ArrayList<>();
        water = 10;
        temperature = 25;
        nutrient = 10;
    }

    public boolean isEmpty() {
        return occupants.isEmpty();
    }

    public Creature getOccupant() {
        if (occupants.isEmpty()) {
            return null;
        }
        return occupants.get(0);
    }

    public void setOccupant(Creature c) {
        occupants.add(c);
    }

    public void clear() {
        occupants.clear();
    }

    public void removeCreature(Creature c) {
        occupants.remove(c);
    }

    public ArrayList<Creature> getOccupants() {
        return occupants;
    }

    public ArrayList<Creature> getCreatures() {
        return occupants;
    }

    public int getWater() {
        return water;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getNutrient() {
        return nutrient;
    }

    @Override
    public void takeTurn(World world) {
        ArrayList<Creature> copy = new ArrayList<>(occupants);

        for (Creature creature : copy) {
            if (occupants.contains(creature)) {
                creature.takeTurn(world);
            }
        }
    }

    public char symbol() {
        if (occupants.isEmpty()) {
            return '.';
        }
        return occupants.get(0).symbol();
    }
}
