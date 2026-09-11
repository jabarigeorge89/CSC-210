public class Hex {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Error: Please enter a hexadecimal number.");
            System.exit(1);
        }

        String hex = args[0];
        long decimal = 0;

        for (int i = 0; i < hex.length(); i++) {

            char c = hex.charAt(i);
            int value;

            if (c >= '0' && c <= '9') {
                value = c - '0';
            }
            else if (c >= 'a' && c <= 'f') {
                value = c - 'a' + 10;
            }
            else if (c >= 'A' && c <= 'F') {
                value = c - 'A' + 10;
            }
            else {
                System.err.println("Error: Not a hexadecimal number.");
                System.exit(1);
                return;
            }

            decimal = decimal * 16 + value;
        }

        System.out.println(decimal);
    }
}