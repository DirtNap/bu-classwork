package edu.bu.cs232.textbook.chapter1;
import javax.swing.JApplet;
import java.awt.Graphics;
public class Exercise19 extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7414933460041994424L;

	public void paint(Graphics painter) {
		painter.drawRect(50, 50, 50, 50);
		painter.drawOval(50, 50, 50, 50);
	}

}
