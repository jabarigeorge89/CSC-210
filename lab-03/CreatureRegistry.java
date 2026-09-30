import java.io.*;
import java.util.ArrayList;

public class CreatureRegistry {

    ArrayList<Creature> creatures = new ArrayList<>();
    String filename;

    // Read creatures from file
    public CreatureRegistry(String filename) throws Exception {
        this.filename = filename;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            String name = parts[0];
            int size = Integer.parseInt(parts[1]);

            creatures.add(new Creature(name, size));
        }

        reader.close();
    }

    // Count creatures
    public int count() {
        return creatures.size();
    }

    // Get a copy of a creature
    public Creature get(int index) {
        Creature creature = creatures.get(index);
        return new Creature(creature.name, creature.size);
    }

    // Change a creature
    public void modify(int index, Creature creature) {
        creatures.set(index, creature);
    }

    // Delete a creature
    public void delete(int index) {
        creatures.remove(index);
    }

    // Add a creature
    public void add(Creature creature) {
        creatures.add(creature);
    }

    // Save creatures to file
    public void save() throws Exception {
        PrintWriter writer = new PrintWriter(filename);

        for (Creature creature : creatures) {
            writer.println(creature.name + "," + creature.size);
        }

        writer.close();
    }

    // Test the registry
    public static void main(String[] args) throws Exception {

        CreatureRegistry registry =
                new CreatureRegistry("creature-data.csv");

        System.out.println("Creatures: " + registry.count());

        registry.add(new Creature("Mermaid", 4));

        registry.modify(0, new Creature("Dragon", 15));

        registry.delete(1);

        registry.save();

        System.out.println("Done!");
    }
}
