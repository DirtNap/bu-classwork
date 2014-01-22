package edu.bu.cs232.textbook.chapter1;

import java.awt.Graphics;
import javax.swing.JApplet;

public class Exercise20 extends JApplet {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4911607912983963897L;

    @Override
    public void paint(Graphics painter) {
        painter.drawArc(50, 50, 50, 50, 90, 180);
        painter.drawArc(60, 50, 40, 50, 90, 180);
    }

}
