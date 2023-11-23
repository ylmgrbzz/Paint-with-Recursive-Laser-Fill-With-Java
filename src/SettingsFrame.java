import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {
    private JTextField widthField;
    private JTextField heightField;
    private JButton continueButton;
    private Controller controller;

    public SettingsFrame(Controller controller) {
        this.controller = controller;
        initializeComponents();
        layoutComponents();
        addListeners();
    }

    private void initializeComponents() {
        widthField = new JTextField(10);
        heightField = new JTextField(10);
        continueButton = new JButton("Continue");
    }

    private void layoutComponents() {
        setTitle("Settings");
        setLayout(new FlowLayout());
        add(new JLabel("Width:"));
        add(widthField);
        add(new JLabel("Height:"));
        add(heightField);
        add(continueButton);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void addListeners() {
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    if (width > 0 && width < 1000 && height > 0 && height < 1000) {
                        controller.setImageSize(width, height);
                        controller.showPaintFrame();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid width or height values.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
                }
            }
        });
    }
}
