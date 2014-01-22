package edu.bu.cs232.textbook.chapter1;

import java.awt.Graphics;
import javax.swing.JApplet;

public class Exercise18 extends JApplet {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3693895976474959809L;

    public void drawRings(Graphics painter, int x, int y, int size, int count) {
        int horizontal = (int) (size * 0.6f);
        int vertical = size / 2;
        for (int i = 0; i < count; ++i) {
            painter.drawOval(x, y, size, size);
            x += horizontal;
            y += vertical;
            vertical *= -1;
        }
    }

    public void drawRings(Graphics painter, int x, int y, int size) {
        this.drawRings(painter, x, y, size, 5);
    }

    public void drawRings(Graphics painter) {
        this.drawRings(painter, 50, 50, 50);
    }

    @Override
    public void paint(Graphics painter) {
        this.drawRings(painter);
    }
}
