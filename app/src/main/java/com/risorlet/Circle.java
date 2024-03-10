package com.risorlet;

import javax.swing.*;
import java.awt.*;

public class Circle extends Component implements Drawable {
    private JPanel surface;

    public Circle(JPanel surface) {
        this.surface = surface;
    }

    @Override
    public void draw(Graphics g,Color c){
        int height = (int) (surface.getHeight() * 0.5);
        int width = (int) (surface.getWidth() * 0.5);
        int x = (int) (surface.getWidth() / 4);
        int y = (int) (surface.getHeight() / 4);
        g.setColor(c);
        g.fillOval(x, y, width, height);
    }

}
