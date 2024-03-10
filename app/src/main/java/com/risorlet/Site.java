package com.risorlet;
import javax.swing.*;
import java.awt.*;

public class Site extends JPanel {
    private Drawable drawable;
    private Color c;
    public Site() {
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public void setDrawable(Drawable drawable, Color c) {
        this.drawable = drawable;
        this.c = c;
    }
    public Drawable getDrawable() {
        return this.drawable;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (drawable != null) {
            drawable.draw(g,c);
        }
    }
}
