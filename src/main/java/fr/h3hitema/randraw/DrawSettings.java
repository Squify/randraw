package fr.h3hitema.randraw;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class DrawSettings {

    private Tool tool = Tool.PEN;
    private Color color = Color.BLACK;
    private int toolSize = 12;

    public enum Tool {
        PEN, BUCKET, ERASER
    }

}
