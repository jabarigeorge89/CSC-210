
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads world.config, builds the World, and runs the simulation.
 *
 * Config format (one entry per line, key=value tokens separated by spaces;
 * blank lines and lines starting with '#' are ignored):
 *
 *   rows=20 cols=40                      <- first entry: world dimensions
 *   species=Bird name=Eagle x=5 y=2      <- one creature per line
 *
 * x is the column (0..cols-1), y is the row (0..rows-1).
 */
public class Main {

    /** Splits a config line like "species=Bird name=Eagle x=5 y=2" into a map. */
    private static Map<String, String> parseLine(String line) {
        Map<String, String> entry = new HashMap<>();
        for (String token : line.trim().split(" ")) {
            if (token.isEmpty()) continue;
            String[] pair = token.split("=");
            if (pair.length == 2) {
                entry.put(pair[0], pair[1]);
            }
        }
        return entry;
    }

    public static void main(String[] args) throws IOException {
        String configFile = args.length > 0 ? args[0] : "world.config";

        World world = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                Map<String, String> entry = parseLine(line);

                if (world == null) {
                    // First real line: world dimensions.
                    int rows = Integer.parseInt(entry.get("rows"));
                    int cols = Integer.parseInt(entry.get("cols"));
                    world = new World(rows, cols);
                    continue;
                }

                // Creature line.
                String species = entry.get("species");
                String name    = entry.get("name");
                int col = Integer.parseInt(entry.get("x"));
                int row = Integer.parseInt(entry.get("y"));

                // TODO (Core): construct the right kind of Creature based on
                // `species` and place it with world.addCreature(creature, row, col).
                // Start with your concrete classes (Bird, Fish, Mammal, and
                // your Plant subclasses).
                //
                // TODO (Stretch): support the optional count=N key — create N
                // creatures, placing the first at (row, col) and the rest at
                // randomly chosen empty tiles.
                //
                // TODO (Stretch): once the species registry exists, look the
                // species up in the registry instead of hard-coding class names.

                if (species.equals("Bird")) {
                    Bird bird = new Bird(name, row, col);
                    world.addCreature(bird, row, col);
                }

                else if (species.equals("Fish")) {
                    Fish fish = new Fish(name, row, col);
                    world.addCreature(fish, row, col);
                }

                else if (species.equals("Mammal")) {
                    Mammal mammal = new Mammal(name, row, col);
                    world.addCreature(mammal, row, col);
                }

                else if (species.equals("Tree")) {
                    Tree tree = new Tree(name, row, col, 10);
                    world.addCreature(tree, row, col);
                }

                else if (species.equals("Flower")) {
                    Flower flower = new Flower(name, row, col, 10);
                    world.addCreature(flower, row, col);
                }

                else if (species.equals("Moss")) {
                    Moss moss = new Moss(name, row, col, 10);
                    world.addCreature(moss, row, col);
                }
            }
        }

        if (world == null) {
            System.err.println("Config file " + configFile + " did not define a world (rows=... cols=...).");
            System.exit(1);
        }

        // TODO (Core): the finished lab runs 100 turns. A few are enough
        // while you are developing.
        for (int i = 0; i < 100; i++) {
            world.print();
            System.out.println();
            world.takeTurn();
        }

        world.print();
    }
}
