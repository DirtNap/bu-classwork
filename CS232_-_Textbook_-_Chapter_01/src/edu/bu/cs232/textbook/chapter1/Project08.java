package edu.bu.cs232.textbook.chapter1;

import java.awt.Graphics;
import javax.swing.JApplet;

public class Project08 extends JApplet {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6011621032603444529L;

    @Override
    public void paint(Graphics painter) {
        CircleCross cc = new CircleCross(painter, 0, 0, 50);
        cc.draw();
    }
}
