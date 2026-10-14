
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MultipleCreatureGUI {

    public static void main(String[] args) {

        ArrayList<Creature> creatures = new ArrayList<Creature>();
        creatures.add(new Creature("Tiger", 5));
        creatures.add(new Creature("Lion", 8));
        creatures.add(new Creature("Dog", 3));

        JFrame frame = new JFrame("Multiple Creature GUI");
        frame.setSize(700, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));

        DefaultListModel<String> listModel = new DefaultListModel<String>();

        for (Creature c : creatures) {
            listModel.addElement(c.name);
        }

        JList<String> creatureList = new JList<String>(listModel);
        frame.add(new JScrollPane(creatureList));

        JPanel middlePanel = new JPanel(new GridLayout(4, 1));

        JTextField nameField = new JTextField();
        JTextField sizeField = new JTextField();

        middlePanel.add(new JLabel("Name:"));
        middlePanel.add(nameField);
        middlePanel.add(new JLabel("Size:"));
        middlePanel.add(sizeField);

        frame.add(middlePanel);

        JPanel rightPanel = new JPanel(new BorderLayout());

        JTextArea display = new JTextArea();
        display.setEditable(false);
        rightPanel.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton saveButton = new JButton("Save");
        JButton eatButton = new JButton("Eat");

        buttons.add(saveButton);
        buttons.add(eatButton);
        rightPanel.add(buttons, BorderLayout.SOUTH);

        frame.add(rightPanel);

        creatureList.addListSelectionListener(e -> {

            int index = creatureList.getSelectedIndex();

            if (index >= 0) {
                Creature c = creatures.get(index);

                nameField.setText(c.name);
                sizeField.setText("" + c.size);

                display.setText("Name: " + c.name +
                                "\nSize: " + c.size);
            }
        });

        saveButton.addActionListener(e -> {

            int index = creatureList.getSelectedIndex();

            if (index >= 0) {
                try {
                    Creature c = creatures.get(index);

                    String newName = nameField.getText();
                    int newSize = Integer.parseInt(sizeField.getText());

                    c.name = newName;
                    c.size = newSize;

                    listModel.set(index, c.name);

                    display.setText("Name: " + c.name +
                                    "\nSize: " + c.size);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter a valid size.");
                }
            }
        });

        eatButton.addActionListener(e -> {

            int index = creatureList.getSelectedIndex();

            if (index >= 0) {
                creatures.get(index).eat();
            }
        });

        creatureList.setSelectedIndex(0);

        frame.setVisible(true);
    }
}
