import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Controller {
    private SettingsFrame settingsFrame;
    private ToolsFrame toolsFrame;
    private PaintFrame paintFrame;
    private BufferedImage image;
    private Color selectedColor = Color.BLACK;

    private Color startedColor;
    private int penSize = 1;
    private int tolerance = 1;
    private String activeTool = "Pen";
    private boolean[][] visited;

    public Controller() {
        settingsFrame = new SettingsFrame(this);
        toolsFrame = new ToolsFrame(this);
        paintFrame = new PaintFrame(this);
        settingsFrame.setVisible(true);
    }

    public void setImageSize(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        clearCanvas();
        paintFrame.setImage(image);
        visited = new boolean[width][height];
    }

    public void showPaintFrame() {
        if (image == null) {
            return;
        }
        paintFrame.setImage(image);


        paintFrame.setSize(image.getWidth(), image.getHeight());
        paintFrame.setLocationRelativeTo(null);
        toolsFrame.pack();
        toolsFrame.setLocation(paintFrame.getX() - toolsFrame.getWidth(), paintFrame.getY());

        paintFrame.setVisible(true);
        toolsFrame.setVisible(true);
    }



    public void setActiveTool(String tool) {
        this.activeTool = tool;
    }

    public void setPenSize(int size) {
        this.penSize = size;
    }

    public void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public void clearCanvas() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
        paintFrame.repaint();
        visited = new boolean[image.getWidth()][image.getHeight()];
    }

    public void handleMouseEvent(Point p) {
        if (activeTool.equals("Pen")) {
            draw(p.x, p.y);
        } else if (activeTool.equals("Laser")) {
            startedColor = new Color(image.getRGB(p.x, p.y));
            laserFill(p.x, p.y);
        }
    }

    private void draw(int x, int y) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(selectedColor);
        g2d.fillRect(x - penSize, y - penSize, penSize * 2 + 1, penSize * 2 + 1);
        g2d.dispose();
        paintFrame.repaint();
    }

    private void laserFill(int x, int y) {
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
            return;
        }
        if (visited[x][y]) {
            return;
        }
        Color currentColor = new Color(image.getRGB(x, y));
        if (!isColorSimilar(currentColor, startedColor)) {
            return;
        }

        visited[x][y] = true;
        image.setRGB(x, y, selectedColor.getRGB());
        laserFill(x, y - 1); // Up
        laserFill(x, y + 1); // Down
        paintFrame.repaint();
    }

    private boolean isColorSimilar(Color a, Color b) {
        return Math.abs(a.getRed() - b.getRed()) + Math.abs(a.getGreen() - b.getGreen()) + Math.abs(a.getBlue() - b.getBlue()) / 3 < tolerance;
    }

}
