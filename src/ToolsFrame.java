import javax.swing.*;
import java.awt.*;

public class ToolsFrame extends JFrame {
    private Controller controller;
    private JButton clearButton;
    private JButton penButton;
    private JButton penSizeButton;
    private JButton colorButton;
    private JButton laserButton;
    private JButton toleranceButton;

    public ToolsFrame(Controller controller) {
        this.controller = controller;
        initializeComponents();
        layoutComponents();
        addListeners();
    }

    private void initializeComponents() {
        clearButton = new JButton("Clear");
        penButton = new JButton("Pen");
        penSizeButton = new JButton("Pen Size");
        colorButton = new JButton("Color");
        laserButton = new JButton("Laser");
        toleranceButton = new JButton("Tolerance");
    }

    private void layoutComponents() {
        setTitle("Tools");
        setLayout(new GridLayout(0, 1));
        add(clearButton);
        add(penButton);
        add(penSizeButton);
        add(colorButton);
        add(laserButton);
        add(toleranceButton);
        pack();
        setLocationRelativeTo(null);
    }

    private void addListeners() {
        clearButton.addActionListener(e -> controller.clearCanvas());
        penButton.addActionListener(e -> controller.setActiveTool("Pen"));
        penSizeButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter pen size:", "Pen Size", JOptionPane.PLAIN_MESSAGE);
            try {
                int size = Integer.parseInt(input);
                controller.setPenSize(size);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid pen size.");
            }
        });
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Pick a Color", Color.WHITE);
            if (color != null) {
                controller.setSelectedColor(color);
            }
        });
        laserButton.addActionListener(e -> controller.setActiveTool("Laser"));
        toleranceButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter tolerance level (0-255)", "Tolerance", JOptionPane.PLAIN_MESSAGE);
            try {
                int tolerance = Integer.parseInt(input);
                controller.setTolerance(tolerance);
            }
            catch (NullPointerException ex) {
                return;
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid tolerance.");
            }
        });
    }
}
