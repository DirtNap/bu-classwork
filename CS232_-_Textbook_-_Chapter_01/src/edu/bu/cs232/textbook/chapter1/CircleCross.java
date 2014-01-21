package edu.bu.cs232.textbook.chapter1;
import java.awt.Graphics;
public class CircleCross {
	private Graphics painter;
	private int size;
	private int y;
	private int x;

	public void draw() {
		int outlineSize = this.size * 2;
		int arcSize = this.size * 3;
		int halfArc = (int)(arcSize / 2.0f);
		int outlineWidth = (int)(this.size * 0.5f);
		
		this.painter.drawArc(this.x + this.size, this.y - halfArc, arcSize, arcSize, 180, 180);
		this.painter.drawArc(this.x - halfArc, this.y + size, arcSize, arcSize, 270, 180);
		this.painter.drawOval(this.x + halfArc, this.y + halfArc, outlineSize, outlineSize);
		this.painter.fillOval(this.x + halfArc+ outlineWidth, this.y + halfArc + outlineWidth, this.size, this.size);
		this.painter.drawArc(this.x + outlineSize + halfArc, this.y + size, arcSize, arcSize, 90, 180);
		this.painter.drawArc(this.x + this.size, this.y + halfArc + outlineSize, arcSize, arcSize, 0, 180);
	}
	
	public CircleCross(Graphics painter, int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.painter = painter;
	}
}
