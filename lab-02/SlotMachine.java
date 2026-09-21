import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class SlotMachine {

    private char slot1;
    private char slot2;
    private char slot3;
    private double moneyPot;

    public SlotMachine() {
        moneyPot = 1000000.00;
    }

    public SlotMachine(String filename) {
        try {
            Scanner file = new Scanner(new File(filename));
            moneyPot = file.nextDouble();
            file.close();
        } catch (Exception e) {
            moneyPot = 1000000.00;
        }
    }

    public double pullLever(double amount) {

        Random random = new Random();

        char[] symbols = {'S', 'H', '7'};

        slot1 = symbols[random.nextInt(3)];
        slot2 = symbols[random.nextInt(3)];
        slot3 = symbols[random.nextInt(3)];

        if (slot1 == slot2 && slot2 == slot3) {

            double winnings = amount * 10;

            if (winnings > moneyPot) {
                winnings = moneyPot;
            }

            moneyPot = moneyPot - winnings;
            return winnings;
        }

        moneyPot = moneyPot + amount;
        return 0;
    }

    public String toString() {
        return "" + slot1 + " " + slot2 + " " + slot3;
    }

    public double getMoneyPot() {
        return moneyPot;
    }

    public void save(String filename) {
        try {
            FileWriter file = new FileWriter(filename);
            file.write("" + moneyPot);
            file.close();
        } catch (Exception e) {
            System.out.println("Could not save slot machine.");
        }
    }
}