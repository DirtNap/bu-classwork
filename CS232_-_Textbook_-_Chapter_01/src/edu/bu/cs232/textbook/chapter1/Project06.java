package edu.bu.cs232.textbook.chapter1;

import javax.swing.JApplet;
import java.awt.Graphics;
public class Project06 extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1049464137141017859L;

	public void paint(Graphics painter) {
		Snowman s = new Snowman(painter, 0, 0, 50);
		s.draw();
	}
}
