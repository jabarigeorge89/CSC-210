
import javax.swing.*;
import java.awt.*;

public class SingleCreatureGUI {

    public static void main(String[] args) {

        Creature creature = new Creature("Tiger", 5);

        JFrame frame = new JFrame("Single Creature GUI");
        frame.setSize(550, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new GridLayout(3, 2));

        JTextField nameField = new JTextField(creature.name);
        JTextField sizeField = new JTextField("" + creature.size);

        leftPanel.add(new JLabel("Name:"));
        leftPanel.add(nameField);

        leftPanel.add(new JLabel("Size:"));
        leftPanel.add(sizeField);

        JButton eatButton = new JButton("Eat");
        leftPanel.add(eatButton);

        JTextArea display = new JTextArea();
        display.setEditable(false);
        display.setText("Name: " + creature.name +
                        "\nSize: " + creature.size);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(display), BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        rightPanel.add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            try {
                creature.name = nameField.getText();
                creature.size = Integer.parseInt(sizeField.getText());

                display.setText("Name: " + creature.name +
                                "\nSize: " + creature.size);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a valid number for size.");
            }
        });

        eatButton.addActionListener(e -> {
            creature.eat();
        });

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
