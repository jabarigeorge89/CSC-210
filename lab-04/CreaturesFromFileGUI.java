
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreaturesFromFileGUI {

    public static void main(String[] args) throws Exception {

        String filename = "creature-data.csv";

        ProcessCreatureFile processor = new ProcessCreatureFile(filename);
        ArrayList<Creature> creatures = processor.getCreatures();

        JFrame frame = new JFrame("Creatures From File GUI");
        frame.setSize(750, 400);
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
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton eatButton = new JButton("Eat");

        buttons.add(saveButton);
        buttons.add(addButton);
        buttons.add(removeButton);
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
                    String name = nameField.getText();
                    int size = Integer.parseInt(sizeField.getText());

                    processor.updateCreature(index, name, size);
                    listModel.set(index, name);

                    display.setText("Name: " + name +
                                    "\nSize: " + size);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Enter a valid size.");
                }
            }
        });

        addButton.addActionListener(e -> {

            try {
                String name = nameField.getText();
                int size = Integer.parseInt(sizeField.getText());

                processor.addCreature(name, size);
                listModel.addElement(name);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter a valid size.");
            }
        });

        removeButton.addActionListener(e -> {

            int index = creatureList.getSelectedIndex();

            if (index >= 0) {
                processor.removeCreature(index);
                listModel.remove(index);
                display.setText("");
                nameField.setText("");
                sizeField.setText("");
            }
        });

        eatButton.addActionListener(e -> {

            int index = creatureList.getSelectedIndex();

            if (index >= 0) {
                creatures.get(index).eat();
            }
        });

        if (creatures.size() > 0) {
            creatureList.setSelectedIndex(0);
        }

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    processor.save(filename);
                } catch (Exception ex) {
                    System.out.println("Could not save file.");
                }
            }
        });

        frame.setVisible(true);
    }
}
