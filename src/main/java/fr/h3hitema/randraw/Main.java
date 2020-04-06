package fr.h3hitema.randraw;

import fr.h3hitema.randraw.view.RandrawFrame;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            Texts.loadTexts();
        } catch (IOException e) {
            System.err.println("Unable to load texts, aborting");
            e.printStackTrace();
            return;
        }
        SwingUtilities.invokeLater(RandrawFrame::new);
    }

}
