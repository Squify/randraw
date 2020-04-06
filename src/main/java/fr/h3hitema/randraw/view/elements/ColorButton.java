package fr.h3hitema.randraw.view.elements;

import fr.h3hitema.randraw.DrawSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ColorButton extends ToolButton {

    private static final double DIAM = 30;
    private static final Shape SHAPE = new Ellipse2D.Double((SIZE.width - DIAM) / 2D, (SIZE.height - DIAM) / 2D, DIAM, DIAM);

    private final DrawSettings drawSettings;

    public ColorButton(DrawSettings drawSettings) {
        super();
        this.drawSettings = drawSettings;
        this.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Color lol", drawSettings.getColor());
            if (newColor != null) {
                drawSettings.setColor(newColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(drawSettings.getColor());
        g2d.fill(SHAPE);
        g2d.dispose();
    }
}
