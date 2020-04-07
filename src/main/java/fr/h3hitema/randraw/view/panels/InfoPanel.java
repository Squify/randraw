package fr.h3hitema.randraw.view.panels;

import fr.h3hitema.randraw.Texts;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfoPanel extends JPanel {

    private Thread currentAnimation;

    public InfoPanel(DrawingPanel drawingPanel) {
        JButton randomBtn = new JButton("Randomiser");
        JTextField nameField = new JTextField("Nom");
        JTextField adjectifField = new JTextField("Adjectif");
        nameField.setEnabled(false);
        adjectifField.setEnabled(false);
        nameField.setPreferredSize(new Dimension(200, 25));
        adjectifField.setPreferredSize(new Dimension(200, 25));
        randomBtn.addActionListener(a -> {
            if (currentAnimation != null) return;
            randomBtn.setEnabled(false);
            drawingPanel.clearShapes();
            currentAnimation = new Thread(() -> {
                List<String> names = Texts.getNames();
                List<String> adjectives = Texts.getAdjectives();
                Collections.shuffle(names);
                Collections.shuffle(adjectives);

                int pauseTime = 50;
                int duration = (int) TimeUnit.SECONDS.toMillis(1);
                int duration2 = (int) TimeUnit.SECONDS.toMillis(3);
                int time = 0;
                int cpt = 0;

                while (time < duration2) {
                    String name = names.get(cpt % names.size());
                    String adjective = adjectives.get(cpt % adjectives.size());
                    int finalTime = time;
                    SwingUtilities.invokeLater(() -> {
                        if (finalTime < duration) {
                            nameField.setText(name);
                        }
                        adjectifField.setText(adjective);
                    });
                    time += pauseTime;
                    cpt++;
                    try {
                        Thread.sleep(pauseTime - 15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                SwingUtilities.invokeLater(() -> randomBtn.setEnabled(true));
                currentAnimation = null;
            });
            currentAnimation.start();
        });
        add(randomBtn);
        add(nameField);
        add(adjectifField);
    }

}
