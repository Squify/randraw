package fr.h3hitema.randraw.view.panels;

import fr.h3hitema.randraw.DrawSettings;
import fr.h3hitema.randraw.view.elements.ColorButton;
import fr.h3hitema.randraw.view.elements.ToolButton;
import fr.h3hitema.randraw.view.elements.ToolSizeButton;

import javax.swing.*;

public class ToolboxPanel extends JPanel {

    public ToolboxPanel(DrawSettings drawSettings, DrawingPanel drawingPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new ToolButton("pen", e -> drawSettings.setTool(DrawSettings.Tool.PEN)));
        this.add(new ToolButton("eraser", e -> drawSettings.setTool(DrawSettings.Tool.ERASER)));
        this.add(new ToolButton("bucket", e -> drawSettings.setTool(DrawSettings.Tool.BUCKET)));
        this.add(new ToolSizeButton(drawSettings));
        this.add(new ColorButton(drawSettings));
        this.add(new ToolButton("cleaner", e -> drawingPanel.clearShapes()));
    }

}
