public class CreatureCLI {

    public static void main(String[] args) {

        try {
            if (args.length < 2) {
                help();
                System.exit(1);
            }

            CreatureRegistry registry =
                    new CreatureRegistry("creature-data.csv");

            String command = args[0];

            // Read
            if (command.equals("read")) {
                int index = Integer.parseInt(args[1]);

                Creature creature = registry.get(index);

                System.out.println(creature.name + "," + creature.size);
            }

            // Delete
            else if (command.equals("delete")) {
                int index = Integer.parseInt(args[1]);

                registry.delete(index);
                registry.save();

                System.out.println("Creature deleted.");
            }

            // Create
            else if (command.equals("create")) {
                String[] data = args[1].split(" ");

                String name = data[0].split(":")[1];
                int size = Integer.parseInt(data[1].split(":")[1]);

                registry.add(new Creature(name, size));
                registry.save();

                System.out.println("Creature created.");
            }

            // Update
            else if (command.equals("update")) {

                if (args.length < 3) {
                    help();
                    System.exit(1);
                }

                int index = Integer.parseInt(args[1]);

                String[] data = args[2].split(" ");

                String name = data[0].split(":")[1];
                int size = Integer.parseInt(data[1].split(":")[1]);

                registry.modify(index, new Creature(name, size));
                registry.save();

                System.out.println("Creature updated.");
            }

            else {
                help();
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void help() {
        System.out.println("Commands:");
        System.out.println("java CreatureCLI create 'name:dragon size:10'");
        System.out.println("java CreatureCLI read 0");
        System.out.println("java CreatureCLI delete 0");
        System.out.println("java CreatureCLI update 0 'name:dragon size:12'");
    }
}
