import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HelloGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Hello GUI");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("");
        JButton button = new JButton("Greet");

        String[] greetings = {
            "Hello!",
            "Hi!",
            "Good morning!",
            "How are you?",
            "Welcome!"
        };

        Random random = new Random();

        button.addActionListener(e -> {
            int number = random.nextInt(5);
            label.setText(greetings[number]);
        });

        frame.add(label);
        frame.add(button);

        frame.setVisible(true);
    }
}