import javax.swing.*;
import java.awt.*;

public class RabbitPopulationGraph extends JFrame {

    private JTextField growthRate = new JTextField("3.5", 5);
    private JTextField initialPopulation = new JTextField("0.5", 5);
    private JSlider iterations = new JSlider(10, 500, 100);
    private GraphPanel graph = new GraphPanel();

    public RabbitPopulationGraph() {
        setTitle("Rabbit Population Graph");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel controls = new JPanel();

        controls.add(new JLabel("Growth Rate (r):"));
        controls.add(growthRate);

        controls.add(new JLabel("Initial Population:"));
        controls.add(initialPopulation);

        controls.add(new JLabel("Iterations:"));
        controls.add(iterations);

        JButton update = new JButton("Update");
        controls.add(update);

        add(controls, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);

        update.addActionListener(e -> graph.repaint());
        iterations.addChangeListener(e -> graph.repaint());

        setVisible(true);
    }

    class GraphPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            double r;
            double x;

            try {
                r = Double.parseDouble(growthRate.getText());
                x = Double.parseDouble(initialPopulation.getText());

                if (!Double.isFinite(r) || !Double.isFinite(x)
                        || r < 0 || r > 4 || x < 0 || x > 1) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                g.drawString("Enter r between 0 and 4 and population between 0 and 1.", 50, 50);
                return;
            }

            int n = iterations.getValue();
            int width = getWidth() - 100;
            int height = getHeight() - 100;

            g.setColor(Color.BLACK);
            g.drawLine(50, 50, 50, height + 50);
            g.drawLine(50, height + 50, width + 50, height + 50);

            g.drawString("Population", 10, 30);
            g.drawString("1.0", 20, 55);
            g.drawString("0.0", 20, height + 55);
            g.drawString("Generation", width / 2, height + 85);

            g.setColor(Color.MAGENTA);

            int previousX = 50;
            int previousY = 50 + (int) ((1 - x) * height);

            for (int i = 1; i <= n; i++) {

                x = r * x * (1 - x);

                int currentX = 50 + i * width / n;
                int currentY = 50 + (int) ((1 - x) * height);

                g.drawLine(previousX, previousY, currentX, currentY);
                g.fillOval(currentX - 2, currentY - 2, 4, 4);

                previousX = currentX;
                previousY = currentY;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RabbitPopulationGraph::new);
    }
}