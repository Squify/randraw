package fr.h3hitema.randraw.view.panels;

import fr.h3hitema.randraw.DrawSettings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends JPanel {

    private final DrawSettings drawSettings;

    @Getter
    private final BufferedImage drawingImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
    private final Graphics2D drawing = drawingImage.createGraphics();

    public DrawingPanel(DrawSettings drawSettings) {
        this.drawSettings = drawSettings;
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(600, 450));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                if (drawSettings.getTool() == DrawSettings.Tool.BUCKET) {
                    int targetColor = drawingImage.getRGB(clickPoint.x, clickPoint.y);
                    int replacementColor = drawSettings.getColor().getRGB();
                    bucketFill(clickPoint.x, clickPoint.y, targetColor, replacementColor);
                }
                else {
                    addShape(clickPoint);
                }
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addShape(e.getPoint());
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawingImage, 0, 0, this);
    }

    public void addShape(Point clickPoint) {
        if (drawSettings.getTool() != DrawSettings.Tool.BUCKET) {
            ShapeWrapper shape = buildShape(clickPoint);
            drawing.setColor(shape.getColor());
            drawing.fill(shape.getShape());
            SwingUtilities.invokeLater(this::repaint);
        }
    }

    private void bucketFill(int x, int y, int targetColor, int replacementColor) {
        if (targetColor == replacementColor) return;
        drawingImage.setRGB(x, y, replacementColor);
        List<Long> pixels = new ArrayList<>(512);
        pixels.add(node(x, y));

        long node;
        int nodeX;
        int nodeY;

        while(!pixels.isEmpty()) {
            node = pixels.remove(0);
            nodeX = nodeX(node);
            nodeY = nodeY(node);
            processNode(nodeX-1, nodeY, targetColor, replacementColor, pixels);
            processNode(nodeX+1, nodeY, targetColor, replacementColor, pixels);
            processNode(nodeX, nodeY-1, targetColor, replacementColor, pixels);
            processNode(nodeX, nodeY+1, targetColor, replacementColor, pixels);
        }
        SwingUtilities.invokeLater(this::repaint);
    }

    private void processNode(int x, int y, int targetColor, int replacementColor, List<Long> pixels) {
        if (x < 0 || y < 0 || x >= drawingImage.getWidth() || y >= drawingImage.getHeight()) return;
        if(drawingImage.getRGB(x, y) == targetColor) {
            drawingImage.setRGB(x, y, replacementColor);
            pixels.add(node(x, y));
        }
    }

    public void clearShapes() {
        drawing.setColor(getForeground());
        drawing.fillRect(0, 0, drawingImage.getWidth(), drawingImage.getHeight());
        SwingUtilities.invokeLater(this::repaint);
    }

    public ShapeWrapper buildShape(Point clickPoint) {
        Color color = drawSettings.getColor();
        double diam = drawSettings.getToolSize();
        if (drawSettings.getTool() == DrawSettings.Tool.ERASER) {
            color = getForeground();
            diam *= 1.5;
        }
        double xPos = clickPoint.getX() - (diam / 2);
        double yPos = clickPoint.getY() - (diam / 2);
        return new ShapeWrapper(new Ellipse2D.Double(xPos, yPos, diam, diam), color);
    }

    @RequiredArgsConstructor
    @Getter
    public static class ShapeWrapper {
        private final Shape shape;
        private final Color color;
    }

    private static long node(int x, int y) {
        return (long) x | ((long) y) << 32;
    }

    private static int nodeX(long node) {
        return (int) (node & 0xffff);
    }

    private static int nodeY(long node) {
        return (int) ((node >> 32) & 0xffff);
    }
}
