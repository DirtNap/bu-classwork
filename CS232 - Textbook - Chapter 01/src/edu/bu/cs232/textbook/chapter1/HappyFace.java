package edu.bu.cs232.textbook.chapter1;
import java.awt.Graphics;
public class HappyFace {
	private Graphics painter;
	private int y;
	private int x;
	private int size;
	private float part;

	public void draw() {
		this.drawHead();
	}
	private void drawHead() {
		this.painter.drawOval(this.x, this.y, this.size, this.size);
		this.drawEyes();
		this.drawNose();
		this.drawMouth();
	}
	public HappyFace(Graphics painter, int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.painter = painter;
		this.part = this.size * 0.1f;
		
	}
	private void drawEyes() {
		int marginY = (int)(this.part * 2);
		int marginX = (int)(this.part * 2.5f);
		int height = (int)(this.part * 1.5f);
		int width = (int)(this.part);
		this.painter.fillOval(this.x + marginX,
				this.y + marginY, width, height);
		this.painter.fillOval(this.x + this.size - marginX - width, marginY, width, height);
	}
	private void drawNose() {
		int marginY = (int)(this.part * 4);
		int marginX = (int)((this.part * 4.0) + (this.part * 0.5));
		int height = (int)(this.part * 2);
		int width = (int)this.part;
		this.painter.fillOval(this.x + marginX, this.y + marginY, width, height);
	}
	private void drawMouth() {
		int marginX = (int)(this.part * 1.5);
		int marginY = (int)(this.part * 4.5);
		int width = (int)(this.part * 7);
		this.painter.drawArc(this.x + marginX, this.y + this.size - marginY - marginX, width, marginY, 180, 180);
	}
}
