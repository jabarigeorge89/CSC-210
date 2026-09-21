import java.util.Scanner;

public class GoodCasino {

    public static double play(Customer customer, SlotMachine machine,
                              double amount) {

        double moneySpent = customer.spend(amount);
        double winnings = machine.pullLever(moneySpent);

        return winnings;
    }

    public static void main(String[] args) {

        Customer customer = new Customer("customer.txt");
        SlotMachine machine = new SlotMachine("slot-machine.txt");

        Scanner input = new Scanner(System.in);

        while (customer.checkWallet() > 0 && machine.getMoneyPot() > 0) {

            System.out.println("Wallet: $" + customer.checkWallet());
            System.out.print("Enter amount to play or type quit: ");

            String answer = input.nextLine();

            if (answer.equalsIgnoreCase("quit")) {
                break;
            }

            double amount;

            try {
                amount = Double.parseDouble(answer);
            } catch (Exception e) {
                System.out.println("Please enter a number.");
                continue;
            }

            if (amount <= 0) {
                System.out.println("Enter an amount greater than 0.");
                continue;
            }

            double winnings = play(customer, machine, amount);

            System.out.println("Slots: " + machine);
            System.out.println("You won: $" + winnings);

            customer.receive(winnings);
        }

        customer.save("customer.txt");
        machine.save("slot-machine.txt");

        input.close();

        System.out.println("Game over.");
        System.out.println("Final wallet: $" + customer.checkWallet());
    }
}