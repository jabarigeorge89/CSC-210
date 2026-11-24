import java.awt.*;
import java.io.*;
import javax.swing.*;
import ecosystem.*;

public class EcosystemGUI {

    private JFrame frame;
    private JPanel gridPanel;
    private JTextArea cellInfo, activityLog;
    private JButton stepButton, playButton;
    private JButton resetButton, loadButton, saveButton;
    private JTextField speedInput;
    private World world;
    private Timer timer;
    private String configFile = "world.config";

    public EcosystemGUI() {
        frame = new JFrame("Ecosystem Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gridPanel = new JPanel();
        frame.add(gridPanel, BorderLayout.CENTER);

        cellInfo = new JTextArea(15, 25);
        cellInfo.setEditable(false);
        cellInfo.setText("Select a cell to view its information.");

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(new JLabel("Cell Information"), BorderLayout.NORTH);
        sidebar.add(new JScrollPane(cellInfo), BorderLayout.CENTER);
        frame.add(sidebar, BorderLayout.EAST);

        activityLog = new JTextArea(8, 40);
        activityLog.setEditable(false);

        stepButton = new JButton("Step");
        playButton = new JButton("Play");
        resetButton = new JButton("Reset");
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        speedInput = new JTextField("500", 5);

        JPanel controlPanel = new JPanel();
        controlPanel.add(stepButton);
        controlPanel.add(playButton);
        controlPanel.add(resetButton);
        controlPanel.add(loadButton);
        controlPanel.add(saveButton);
        controlPanel.add(new JLabel("Speed (ms):"));
        controlPanel.add(speedInput);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JScrollPane(activityLog), BorderLayout.CENTER);
        bottomPanel.add(controlPanel, BorderLayout.SOUTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        timer = new Timer(500, e -> step());

        stepButton.addActionListener(e -> step());
        playButton.addActionListener(e -> togglePlay());
        resetButton.addActionListener(e -> loadWorld(configFile));
        loadButton.addActionListener(e -> chooseFile());
        saveButton.addActionListener(e -> saveWorld());

        loadWorld(configFile);

        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadWorld(String file) {
        timer.stop();
        playButton.setText("Play");

        try {
            world = ecosystem.Main.loadWorld(file);
            configFile = file;
            activityLog.setText("Loaded: " + file + "\n");
            cellInfo.setText("Select a cell.");
            refreshGrid();
        } catch (Exception e) {
            activityLog.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void refreshGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(
                world.getRows(), world.getCols()));

        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {

                Tile tile = world.getTile(row, col);
                JButton button = new JButton();
                button.setMargin(new Insets(0, 0, 0, 0));

                if (tile.isEmpty()) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setText(String.valueOf(tile.symbol()));
                    button.setBackground(Color.GREEN);
                }

                final int r = row;
                final int c = col;

                button.addActionListener(e -> showCell(r, c));
                gridPanel.add(button);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void showCell(int row, int col) {
        Tile tile = world.getTile(row, col);

        StringBuilder info = new StringBuilder();

        info.append("Row: ").append(row).append("\n");
        info.append("Column: ").append(col).append("\n");
        info.append("Water: ").append(tile.getWater()).append("\n");
        info.append("Temperature: ").append(tile.getTemperature()).append("\n");
        info.append("Nutrients: ").append(tile.getNutrient()).append("\n\n");

        for (Creature creature : tile.getOccupants()) {
            info.append("Species: ")
                .append(creature.getClass().getSimpleName()).append("\n");
            info.append("Name: ").append(creature.getName()).append("\n");
            info.append("Energy: ").append(creature.getEnergy()).append("\n\n");
        }

        cellInfo.setText(info.toString());
    }

    private void step() {
        if (world == null) return;

        world.takeTurn();
        refreshGrid();

        activityLog.append("Turn " + world.getTurn()
                + " completed.\n");
    }

    private void togglePlay() {
        if (timer.isRunning()) {
            timer.stop();
            playButton.setText("Play");
        } else {
            try {
                int speed = Integer.parseInt(speedInput.getText());

                if (speed <= 0) {
                    throw new NumberFormatException();
                }

                timer.setDelay(speed);
                timer.start();
                playButton.setText("Stop");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame,
                        "Enter a valid positive speed.");
            }
        }
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser(".");

        if (chooser.showOpenDialog(frame)
                == JFileChooser.APPROVE_OPTION) {

            loadWorld(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void saveWorld() {
        if (world == null) return;

        JFileChooser chooser = new JFileChooser(".");
        chooser.setSelectedFile(new File("saved-world.config"));

        if (chooser.showSaveDialog(frame)
                == JFileChooser.APPROVE_OPTION) {

            try (PrintWriter writer =
                    new PrintWriter(chooser.getSelectedFile())) {

                writer.println("rows=" + world.getRows()
                        + " cols=" + world.getCols());

                for (Creature creature : world.getCreatures()) {
                    writer.println("species="
                            + creature.getClass().getSimpleName()
                            + " name=" + creature.getName()
                            + " x=" + creature.getCol()
                            + " y=" + creature.getRow());
                }

                activityLog.append("World configuration saved.\n");

            } catch (IOException e) {
                activityLog.append("Save error: "
                        + e.getMessage() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcosystemGUI::new);
    }
}