package fr.h3hitema.randraw.view;

import fr.h3hitema.randraw.DrawSettings;
import fr.h3hitema.randraw.view.panels.DrawingPanel;
import fr.h3hitema.randraw.view.panels.InfoPanel;
import fr.h3hitema.randraw.view.panels.ToolboxPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RandrawFrame extends JFrame {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private final DrawSettings drawSettings = new DrawSettings();
    private final DrawingPanel drawingPanel = new DrawingPanel(drawSettings);

    public RandrawFrame() {
        this.setTitle("Randraw, it's fun");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(buildPane());
        this.setJMenuBar(buildMenuBar());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save picture");
        saveMenuItem.addActionListener(a -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File targetFile = chooser.getSelectedFile();
                try {
                    ImageIO.write(drawingPanel.getDrawingImage(), "PNG", targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileMenu.add(saveMenuItem);
        menu.add(fileMenu);

        return menu;
    }

    private JPanel buildPane() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new BorderLayout());
        center.add(drawingPanel, BorderLayout.CENTER);
        center.add(new InfoPanel(drawingPanel), BorderLayout.NORTH);
        panel.add(new ToolboxPanel(drawSettings, drawingPanel), BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

}
