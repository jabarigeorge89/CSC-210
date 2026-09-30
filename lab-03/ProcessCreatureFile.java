import java.io.*;
import java.util.ArrayList;

public class ProcessCreatureFile {

    public static void main(String[] args) throws Exception {

        ArrayList<Creature> creatures = new ArrayList<>();

        // Read file
        BufferedReader reader = new BufferedReader(
                new FileReader("creature-data.csv"));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            String name = parts[0];
            int size = Integer.parseInt(parts[1]);

            creatures.add(new Creature(name, size));
        }

        reader.close();

        // Add a creature
        creatures.add(new Creature("Unicorn", 6));

        // Remove a creature
        creatures.remove(0);

        // Change a creature
        creatures.get(0).name = "Dragon";
        creatures.get(0).size = 12;

        // Write back to file
        PrintWriter writer = new PrintWriter("creature-data.csv");

        for (Creature creature : creatures) {
            writer.println(creature.name + "," + creature.size);
        }

        writer.close();

        System.out.println("Done!");
    }
}
