import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Customer {

    private double wallet;

    public Customer() {
        wallet = 500.00;
    }

    public Customer(String filename) {
        try {
            Scanner file = new Scanner(new File(filename));
            wallet = file.nextDouble();
            file.close();
        } catch (Exception e) {
            wallet = 500.00;
        }
    }

    public double spend(double amount) {

        if (amount > wallet) {
            double moneyLeft = wallet;
            wallet = 0;
            return moneyLeft;
        }

        wallet = wallet - amount;
        return amount;
    }

    public void receive(double amount) {
        wallet = wallet + amount;
    }

    public double checkWallet() {
        return wallet;
    }

    public void save(String filename) {
        try {
            FileWriter file = new FileWriter(filename);
            file.write("" + wallet);
            file.close();
        } catch (Exception e) {
            System.out.println("Could not save customer.");
        }
    }
}