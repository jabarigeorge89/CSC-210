import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class BifurcationDiagram extends JFrame {

    private JSlider minSlider = new JSlider(0, 400, 250);
    private JSlider maxSlider = new JSlider(0, 400, 400);

    private double rMin = 2.5;
    private double rMax = 4.0;
    private double xMin = 0.0;
    private double xMax = 1.0;

    private int columns = Integer.MAX_VALUE;
    private GraphPanel graph = new GraphPanel();
    private Timer timer;

    public BifurcationDiagram() {

        setTitle("Logistic Map - Bifurcation Diagram");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel controls = new JPanel();

        controls.add(new JLabel("Minimum r:"));
        controls.add(minSlider);

        controls.add(new JLabel("Maximum r:"));
        controls.add(maxSlider);

        JButton animate = new JButton("Animate");
        JButton reset = new JButton("Reset Zoom");
        JButton png = new JButton("Export PNG");
        JButton csv = new JButton("Export CSV");

        controls.add(animate);
        controls.add(reset);
        controls.add(png);
        controls.add(csv);

        add(controls, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);

        timer = new Timer(20, e -> {
            columns += 8;
            graph.repaint();

            if (columns >= graph.getWidth() - 100) {
                timer.stop();
                columns = Integer.MAX_VALUE;
                graph.repaint();
            }
        });

        minSlider.addChangeListener(e -> updateRange());
        maxSlider.addChangeListener(e -> updateRange());

        animate.addActionListener(e -> {
            columns = 0;
            timer.restart();
        });

        reset.addActionListener(e -> {
            timer.stop();
            rMin = 2.5;
            rMax = 4.0;
            xMin = 0.0;
            xMax = 1.0;
            minSlider.setValue(250);
            maxSlider.setValue(400);
            columns = Integer.MAX_VALUE;
            graph.repaint();
        });

        png.addActionListener(e -> exportPNG());
        csv.addActionListener(e -> exportCSV());

        setVisible(true);
    }

    private void updateRange() {

        double min = minSlider.getValue() / 100.0;
        double max = maxSlider.getValue() / 100.0;

        if (min < max) {
            timer.stop();
            rMin = min;
            rMax = max;
            columns = Integer.MAX_VALUE;
            graph.repaint();
        }
    }

    class GraphPanel extends JPanel {

        private Point start;
        private Point end;

        GraphPanel() {

            setBackground(Color.WHITE);

            MouseAdapter mouse = new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    start = e.getPoint();
                    end = start;
                }

                public void mouseDragged(MouseEvent e) {
                    end = e.getPoint();
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {

                    if (start == null) return;

                    end = e.getPoint();

                    int width = getWidth() - 100;
                    int height = getHeight() - 100;

                    int left = Math.max(50,
                            Math.min(start.x, end.x));

                    int right = Math.min(width + 50,
                            Math.max(start.x, end.x));

                    int top = Math.max(50,
                            Math.min(start.y, end.y));

                    int bottom = Math.min(height + 50,
                            Math.max(start.y, end.y));

                    if (right - left > 10 &&
                            bottom - top > 10) {

                        double oldRMin = rMin;
                        double oldRMax = rMax;
                        double oldXMin = xMin;
                        double oldXMax = xMax;

                        rMin = oldRMin + (left - 50)
                                * (oldRMax - oldRMin) / width;

                        rMax = oldRMin + (right - 50)
                                * (oldRMax - oldRMin) / width;

                        xMax = oldXMax - (top - 50)
                                * (oldXMax - oldXMin) / height;

                        xMin = oldXMax - (bottom - 50)
                                * (oldXMax - oldXMin) / height;

                        timer.stop();
                        columns = Integer.MAX_VALUE;
                    }

                    start = null;
                    end = null;
                    repaint();
                }
            };

            addMouseListener(mouse);
            addMouseMotionListener(mouse);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            int width = getWidth() - 100;
            int height = getHeight() - 100;

            if (width <= 0 || height <= 0) return;

            g.setColor(Color.BLACK);

            g.drawLine(50, 50, 50, height + 50);
            g.drawLine(50, height + 50,
                    width + 50, height + 50);

            g.drawString("Population (x)", 10, 30);
            g.drawString("Growth Rate (r)",
                    width / 2, height + 85);

            g.drawString(String.format("%.3f", rMin),
                    50, height + 70);

            g.drawString(String.format("%.3f", rMax),
                    width + 10, height + 70);

            g.drawString(String.format("%.2f", xMax),
                    10, 55);

            g.drawString(String.format("%.2f", xMin),
                    10, height + 55);

            g.setColor(Color.BLUE);

            int limit = Math.min(columns, width);

            for (int col = 0; col < limit; col++) {

                double r = rMin + col
                        * (rMax - rMin) / width;

                double x = 0.5;

                // Ignore transient iterations.
                for (int i = 0; i < 500; i++) {
                    x = r * x * (1 - x);
                }

                // Plot long-term population values.
                for (int i = 0; i < 150; i++) {

                    x = r * x * (1 - x);

                    if (x >= xMin && x <= xMax) {

                        int px = 50 + col;

                        int py = 50 + (int)
                                ((xMax - x) /
                                (xMax - xMin) * height);

                        g.fillRect(px, py, 1, 1);
                    }
                }
            }

            if (start != null && end != null) {

                g.setColor(Color.RED);

                g.drawRect(
                        Math.min(start.x, end.x),
                        Math.min(start.y, end.y),
                        Math.abs(start.x - end.x),
                        Math.abs(start.y - end.y));
            }
        }
    }

    private void exportPNG() {

        JFileChooser chooser = new JFileChooser(".");
        chooser.setSelectedFile(new File("bifurcation.png"));

        if (chooser.showSaveDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            try {

                BufferedImage image = new BufferedImage(
                        graph.getWidth(),
                        graph.getHeight(),
                        BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics = image.createGraphics();
                graph.paint(graphics);
                graphics.dispose();

                ImageIO.write(image, "png",
                        chooser.getSelectedFile());

                JOptionPane.showMessageDialog(this,
                        "PNG exported successfully.");

            } catch (IOException e) {

                JOptionPane.showMessageDialog(this,
                        e.getMessage());
            }
        }
    }

    private void exportCSV() {

        JFileChooser chooser = new JFileChooser(".");
        chooser.setSelectedFile(new File("bifurcation.csv"));

        if (chooser.showSaveDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            try (PrintWriter writer = new PrintWriter(
                    chooser.getSelectedFile())) {

                writer.println("r,x");

                int width = Math.max(1, graph.getWidth() - 100);
                int limit = Math.min(columns, width);

                for (int col = 0; col < limit; col++) {

                    double r = rMin + col
                            * (rMax - rMin) / width;

                    double x = 0.5;

                    for (int i = 0; i < 500; i++) {
                        x = r * x * (1 - x);
                    }

                    for (int i = 0; i < 150; i++) {

                        x = r * x * (1 - x);

                        writer.println(r + "," + x);
                    }
                }

                JOptionPane.showMessageDialog(this,
                        "CSV exported successfully.");

            } catch (IOException e) {

                JOptionPane.showMessageDialog(this,
                        e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BifurcationDiagram::new);
    }
}