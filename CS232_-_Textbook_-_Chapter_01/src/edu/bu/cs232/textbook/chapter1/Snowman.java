package edu.bu.cs232.textbook.chapter1;

import java.awt.Graphics;

public class Snowman {
    private Graphics painter;
    private int headSize;
    private int bodySize;
    private int skirtSize;
    private int x;
    private int y;

    private int getMargin(int x, int largest, int current) {
        return (x + (int) ((largest - current) / 2.0f));
    }

    public void draw() {
        int headX = this.getMargin(this.x, this.skirtSize, this.headSize);
        int bodyX = this.getMargin(this.x, this.skirtSize, this.bodySize);
        int skirtX = this.x;
        int headY = this.y;
        int bodyY = this.y + this.headSize;
        int skirtY = bodyY + this.bodySize;
        HappyFace hf = new HappyFace(this.painter, headX, headY, this.headSize);
        hf.draw();
        this.painter.drawOval(bodyX, bodyY, this.bodySize, this.bodySize);
        this.painter.drawOval(skirtX, skirtY, this.skirtSize, this.skirtSize);
    }

    public Snowman(Graphics painter, int x, int y, int headSize) {
        this.painter = painter;
        this.headSize = headSize;
        this.x = x;
        this.y = y;
        this.bodySize = (int) (this.headSize * 1.5f);
        this.skirtSize = this.headSize * 2;

    }
}
