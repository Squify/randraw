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
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class DrawingPanel extends JPanel {

    private final Random rnd = new Random();
    private final DrawSettings drawSettings;

    @Getter
    private final BufferedImage drawingImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
    private final Graphics2D drawing = drawingImage.createGraphics();

    public DrawingPanel(DrawSettings drawSettings) {
        this.drawSettings = drawSettings;
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(600, 450));

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
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
        ShapeWrapper shape = buildShape(clickPoint);
        drawing.setColor(shape.getColor());
        drawing.fill(shape.getShape());
        SwingUtilities.invokeLater(this::repaint);
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
        double xPos = clickPoint.getX() - (diam/2);
        double yPos = clickPoint.getY() - (diam/2);
        return new ShapeWrapper(new Ellipse2D.Double(xPos, yPos, diam, diam), color);
    }

    @RequiredArgsConstructor @Getter
    public static class ShapeWrapper {
        private final Shape shape;
        private final Color color;
    }
}
