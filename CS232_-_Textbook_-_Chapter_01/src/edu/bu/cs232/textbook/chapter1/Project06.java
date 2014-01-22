package edu.bu.cs232.textbook.chapter1;

import java.awt.Graphics;
import javax.swing.JApplet;

public class Project06 extends JApplet {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1049464137141017859L;

    @Override
    public void paint(Graphics painter) {
        Snowman s = new Snowman(painter, 0, 0, 50);
        s.draw();
    }
}
