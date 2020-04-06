package fr.h3hitema.randraw.view.elements;

import fr.h3hitema.randraw.DrawSettings;

import javax.swing.*;

public class ToolSizeButton extends ToolButton {

    private final DrawSettings drawSettings;

    private final JDialog sliderDialog;

    public ToolSizeButton(DrawSettings drawSettings) {
        super("tool-size");
        this.drawSettings = drawSettings;
        JLabel sliderLabel = new JLabel("Taille: " + drawSettings.getToolSize());
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 70, drawSettings.getToolSize());
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(event -> {
            drawSettings.setToolSize(slider.getValue());
            sliderLabel.setText("Taille: " + drawSettings.getToolSize());
        });
        this.sliderDialog = new JDialog();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(slider);
        panel.add(sliderLabel);
        this.sliderDialog.setTitle("Changer la taille du crayon");
        this.sliderDialog.setContentPane(panel);
        this.sliderDialog.setResizable(false);
        this.sliderDialog.setAlwaysOnTop(true);
        this.sliderDialog.pack();

        this.addActionListener(action -> {
            if(!sliderDialog.isVisible()) {
                sliderDialog.setLocationRelativeTo(this);
                sliderDialog.setVisible(true);
            }
        });
    }
}
