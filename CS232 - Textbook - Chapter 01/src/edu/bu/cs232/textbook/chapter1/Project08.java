package edu.bu.cs232.textbook.chapter1;

import javax.swing.JApplet;
import java.awt.Graphics;
public class Project08 extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6011621032603444529L;

	public void paint(Graphics painter) {
		CircleCross cc = new CircleCross(painter, 0, 0, 50);
		cc.draw();
	}
}
