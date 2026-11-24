package ecosystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static Map<String, String> parseLine(String line) {
        Map<String, String> entry = new HashMap<>();

        for (String token : line.trim().split("\\s+")) {
            String[] pair = token.split("=");
            if (pair.length == 2) {
                entry.put(pair[0], pair[1]);
            }
        }

        return entry;
    }

    public static World loadWorld(String configFile) throws IOException {
        World world = null;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(configFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                Map<String, String> entry = parseLine(line);

                if (world == null) {
                    int rows = Integer.parseInt(entry.get("rows"));
                    int cols = Integer.parseInt(entry.get("cols"));
                    world = new World(rows, cols);
                    continue;
                }

                String species = entry.get("species");
                String name = entry.get("name");
                int col = Integer.parseInt(entry.get("x"));
                int row = Integer.parseInt(entry.get("y"));

                Creature creature;

                switch (species) {
                    case "Bird":
                        creature = new Bird(name, row, col);
                        break;
                    case "Fish":
                        creature = new Fish(name, row, col);
                        break;
                    case "Mammal":
                        creature = new Mammal(name, row, col);
                        break;
                    case "Tree":
                        creature = new Tree(name, row, col, 10);
                        break;
                    case "Flower":
                        creature = new Flower(name, row, col, 10);
                        break;
                    case "Moss":
                        creature = new Moss(name, row, col, 10);
                        break;
                    default:
                        continue;
                }

                world.addCreature(creature, row, col);
            }
        }

        if (world == null) {
            throw new IOException("Invalid world configuration.");
        }

        return world;
    }

    public static void main(String[] args) throws IOException {
        String configFile = args.length > 0
                ? args[0] : "world.config";

        World world = loadWorld(configFile);

        for (int i = 0; i < 100; i++) {
            world.print();
            System.out.println();
            world.takeTurn();
        }

        world.print();
    }
}