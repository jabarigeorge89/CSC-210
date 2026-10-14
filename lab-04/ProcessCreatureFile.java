
import java.io.*;
import java.util.ArrayList;

public class ProcessCreatureFile {

    private ArrayList<Creature> creatures = new ArrayList<Creature>();

    public ProcessCreatureFile(String filename) throws Exception {

        File file = new File(filename);

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                int size = Integer.parseInt(parts[1]);

                creatures.add(new Creature(name, size));
            }

            reader.close();
        }
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void addCreature(String name, int size) {
        creatures.add(new Creature(name, size));
    }

    public void updateCreature(int index, String name, int size) {
        Creature creature = creatures.get(index);
        creature.name = name;
        creature.size = size;
    }

    public void removeCreature(int index) {
        creatures.remove(index);
    }

    public void save(String filename) throws Exception {

        PrintWriter writer = new PrintWriter(filename);

        for (Creature creature : creatures) {
            writer.println(creature.name + "," + creature.size);
        }

        writer.close();
    }
}
