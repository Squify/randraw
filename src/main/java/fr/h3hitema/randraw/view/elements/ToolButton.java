package fr.h3hitema.randraw.view.elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ToolButton extends JButton {

    protected static final Dimension SIZE = new Dimension(40, 40);

    public ToolButton() {
        super();
        this.setMinimumSize(SIZE);
        this.setMaximumSize(SIZE);
        this.setPreferredSize(SIZE);
    }

    public ToolButton(String name) {
        this();
        try {
            Image img = ImageIO.read(getClass().getResourceAsStream("/img/buttons/"+name+".png"));
            this.setIcon(new ImageIcon(img));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        this.setName(name);
    }

    public ToolButton(String name, ActionListener action) {
        this(name);
        this.addActionListener(action);
    }
}
