import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class PaintFrame extends JFrame implements MouseInputListener {
    private BufferedImage image;
    private Controller controller;
    private PaintPanel paintPanel;

    public PaintFrame(Controller controller) {
        this.controller = controller;
        setTitle("Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        paintPanel = new PaintPanel();
        add(paintPanel);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setImage(BufferedImage img) {
        this.image = img;
        paintPanel.setImage(img);
    }

    private static class PaintPanel extends JPanel {
        private BufferedImage image;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this);
            }
        }

        public void setImage(BufferedImage img) {
            this.image = img;
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            revalidate();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point adjustedPoint = SwingUtilities.convertPoint(this, e.getPoint(), paintPanel);
        if (isWithinImageBounds(adjustedPoint)) {
            controller.handleMouseEvent(adjustedPoint);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point adjustedPoint = SwingUtilities.convertPoint(this, e.getPoint(), paintPanel);
        if (isWithinImageBounds(adjustedPoint)) {
            controller.handleMouseEvent(adjustedPoint);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point adjustedPoint = SwingUtilities.convertPoint(this, e.getPoint(), paintPanel);
        if (isWithinImageBounds(adjustedPoint)) {
            controller.handleMouseEvent(adjustedPoint);
        }
    }

    private boolean isWithinImageBounds(Point p) {
        return p.x >= 0 && p.x < image.getWidth() && p.y >= 0 && p.y < image.getHeight();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
